using JuegoAhorcadoBlazorServer.Modelo;
using JuegoAhorcadoBlazorServer.Services;

namespace JuegoAhorcadoBlazorServer.Negocio
{
    public class ScoreNegocio
    {
        private readonly Comunicador _comunicador;

        public ScoreNegocio(Comunicador comunicador)
        {
            _comunicador = comunicador;
        }

        public async Task<List<Player>> GetPositions()
        {
            string respuesta = await _comunicador.EnviarYRecibirAsync("score/positions");

            if (string.IsNullOrEmpty(respuesta))
                return new List<Player>();

            return ParsePositions(respuesta);
        }

        public async Task<string> UpdateScore(int points, int rounds)
        {
            string comando = $"score/update/points={points}&rounds={rounds}";
            string respuesta = await _comunicador.EnviarYRecibirAsync(comando);
            return respuesta;
        }

        private List<Player> ParsePositions(string data)
        {
            List<Player> players = new();
            string[] entries = data.Split(';');

            foreach (var entry in entries)
            {
                string[] fields = entry.Split('|');
                if (fields.Length == 3)
                {
                    players.Add(new Player
                    {
                        Name = fields[0],
                        Points = int.Parse(fields[1]),
                        Rounds = int.Parse(fields[2])
                    });
                }
            }

            return players;
        }
    }
    
}