using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class WordListDeserializer
    {
        private readonly WordDeserializer _wordDeserializer = new();

        public List<WordEntry> Deserialize(string value)
        {
            List<WordEntry> words = new();

            if (string.IsNullOrWhiteSpace(value))
                return words;

            string[] items = value.Split(';', StringSplitOptions.RemoveEmptyEntries);

            foreach (string item in items)
            {
                words.Add(_wordDeserializer.Deserialize(item));
            }

            return words;
        }
    }
}
