using JuegoAhorcadoBlazorServer.Services;

namespace JuegoAhorcadoBlazorServer.Negocio
{
    public class GameState
    {
        public string MaskedWord { get; set; } = "";
        public int Lives { get; set; }
        public string Hint { get; set; } = "";
        public string Category { get; set; } = "";
        public string Status { get; set; } = "";
        public string RevealedWord { get; set; } = "";
        public string ErrorMessage { get; set; } = "";
    }

    public class GameNegocio
    {
        private readonly Comunicador _comunicador;

        public GameNegocio(Comunicador comunicador)
        {
            _comunicador = comunicador;
        }

        public async Task<GameState> StartGameAsync()
        {
            string response = await _comunicador.EnviarYRecibirAsync("game/start");
            return ParseResponse(response);
        }

        public async Task<GameState> GuessLetterAsync(char letter)
        {
            string response = await _comunicador.EnviarYRecibirAsync($"game/guess/letter={letter}");
            return ParseResponse(response);
        }

        public async Task<GameState> GetHintAsync()
        {
            string response = await _comunicador.EnviarYRecibirAsync("game/hint");
            return ParseResponse(response);
        }

        private GameState ParseResponse(string response)
        {
            var state = new GameState();

            if (string.IsNullOrWhiteSpace(response))
            {
                state.ErrorMessage = "No se pudo conectar con el servidor.";
                return state;
            }

            if (response.StartsWith("bad="))
            {
                state.ErrorMessage = response.Substring(4);
                return state;
            }

            string[] parts = response.Split('|');

            if (parts.Length < 2)
            {
                state.ErrorMessage = response;
                return state;
            }

            string first = parts[0].Trim();

            if (first == "hint")
            {
                state.Hint = parts.Length > 1 ? parts[1].Trim() : "";
                return state;
            }

            if (first == "won")
            {
                state.Status = "won";
                state.RevealedWord = parts.Length > 1 ? parts[1].Trim() : "";
                state.Lives = parts.Length > 2 && int.TryParse(parts[2].Trim(), out int livesW) ? livesW : 0;
                return state;
            }

            if (first == "lost")
            {
                state.Status = "lost";
                state.RevealedWord = parts.Length > 1 ? parts[1].Trim() : "";
                return state;
            }

            if (parts.Length >= 3 && parts[2].Trim() == "playing")
            {
                state.Status = "playing";
                state.MaskedWord = first;
                state.Lives = int.TryParse(parts[1].Trim(), out int livesP) ? livesP : 0;
                return state;
            }

            if (parts.Length >= 4)
            {
                state.Status = "playing";
                state.MaskedWord = first;
                state.Lives = int.TryParse(parts[1].Trim(), out int livesS) ? livesS : 0;
                state.Hint = parts[2].Trim();
                state.Category = parts[3].Trim();
                return state;
            }

            state.ErrorMessage = "Formato de respuesta no reconocido: " + response;
            return state;
        }
    }
}
