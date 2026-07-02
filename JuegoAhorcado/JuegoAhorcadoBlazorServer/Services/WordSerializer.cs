using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class WordSerializer
    {
        public string Serialize(WordEntry word)
        {
            if (word == null)
                throw new ArgumentNullException(nameof(word));

            return string.Join("|",
                Clean(word.Name),
                Clean(word.Category),
                Clean(word.Hint),
                word.Score.ToString()
            );
        }

        private string Clean(string value)
        {
            return value?.Trim() ?? "";
        }
    }
}
