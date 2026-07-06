using JuegoAhorcadoBlazorServer.Modelo;
using JuegoAhorcadoBlazorServer.Services;
using static JuegoAhorcadoBlazorServer.Components.WordsTab;

namespace JuegoAhorcadoBlazorServer.Negocio
{
    public class WordNegocio
    {
        private readonly Comunicador _comunicador;

        private readonly WordSerializer _wordSerializer = new();
        private readonly WordListDeserializer _wordListDeserializer = new();
        private readonly WordCategoryDeserializer _wordCategoryDeserializer = new();
        private readonly BackendResponseDeserializer _responseDeserializer = new();

        public WordNegocio(Comunicador comunicador)
        {
            _comunicador = comunicador;
        }

        public async Task<List<WordEntry>> GetWordsAsync()
        {
            string request = "admin/get-words";
            string response = await _comunicador.EnviarYRecibirAsync(request);

            if (string.IsNullOrWhiteSpace(response) || response.Trim().Equals("null", StringComparison.OrdinalIgnoreCase))
                return new List<WordEntry>();

            ValidateReadResponse(response);

            return _wordListDeserializer.Deserialize(response);
        }

        public async Task<List<string>> GetCategoriesAsync()
        {
            string request = "admin/get-categories";
            string response = await _comunicador.EnviarYRecibirAsync(request);

            if (string.IsNullOrWhiteSpace(response) || response.Trim().Equals("null", StringComparison.OrdinalIgnoreCase))
                return new List<string>();

            ValidateReadResponse(response);

            return _wordCategoryDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> AddWordAsync(WordEntry word)
        {
            string serializedWord = _wordSerializer.Serialize(word);
            string request = $"admin/add-word/word={serializedWord}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> UpdateWordAsync(string oldWordName, WordEntry newWord)
        {
            string serializedWord = _wordSerializer.Serialize(newWord);

            string request = $"admin/update-word/oldWord={oldWordName}&newWord={serializedWord}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> DeleteWordAsync(string wordName)
        {
            string request = $"admin/delete-word/word={wordName}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> AddCategoryAsync(string categoryName)
        {
            string request = $"admin/add-category/name={Uri.EscapeDataString(categoryName)}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> DeleteCategoryAsync(string categoryName)
        {
            string request = $"admin/delete-category/name={Uri.EscapeDataString(categoryName)}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> UpdateCategoryAsync(string oldName, string newName)
        {
            string request = $"admin/update-category/oldName={Uri.EscapeDataString(oldName)}&newName={Uri.EscapeDataString(newName)}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        private void ValidateReadResponse(string response)
        {
            if (string.IsNullOrWhiteSpace(response))
                throw new InvalidOperationException("El servidor no devolvió datos.");

            BackendResponse backendResponse = _responseDeserializer.Deserialize(response);

            if (backendResponse.IsUnauthorized)
                throw new UnauthorizedAccessException(backendResponse.Message);

            if (!backendResponse.Success && response.StartsWith("bad=", StringComparison.OrdinalIgnoreCase))
                throw new InvalidOperationException(backendResponse.Message);
        }
    }
}
