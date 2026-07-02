namespace JuegoAhorcadoBlazorServer.Services
{
    public class WordCategoryDeserializer
    {
        public List<string> Deserialize(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                return new List<string>();

            return value
                .Split(';', StringSplitOptions.RemoveEmptyEntries)
                .Select(category => category.Trim())
                .Where(category => !string.IsNullOrWhiteSpace(category))
                .ToList();
        }
    }
}
