using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class WordDeserializer
    {
        public WordEntry Deserialize(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentException("La palabra recibida está vacía.");

            string[] values = value.Split('|');

            if (values.Length < 4)
                throw new FormatException("El formato de palabra recibido no es válido.");

            return new WordEntry
            {
                Name = values[0],
                Category = values[1],
                Hint = values[2],
                Score = int.TryParse(values[3], out int score) ? score : 0
            };
        }
    }
}
