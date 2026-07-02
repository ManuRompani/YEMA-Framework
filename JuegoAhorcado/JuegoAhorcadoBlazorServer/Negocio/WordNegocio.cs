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

            return _wordListDeserializer.Deserialize(response);
        }

        public async Task<List<string>> GetCategoriesAsync()
        {
            string request = "admin/get-categories";
            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _wordCategoryDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> AddWordAsync(WordEntry word)
        {
            string serializedWord = _wordSerializer.Serialize(word);
            string request = $"admin/add-word/word={serializedWord}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> UpdateWordAsync(WordEntry word)
        {
            string serializedWord = _wordSerializer.Serialize(word);
            string request = $"admin/update-word/word={serializedWord}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }

        public async Task<BackendResponse> DeleteWordAsync(string wordName)
        {
            string request = $"admin/delete-word/word={wordName}";

            string response = await _comunicador.EnviarYRecibirAsync(request);

            return _responseDeserializer.Deserialize(response);
        }
    }
}
