namespace JuegoAhorcadoBlazorServer.Modelo
{
    public class Word
    {
        public string Name { get; set; } = string.Empty;
        public string Category { get; set; } = string.Empty;
        public string Hint { get; set; } = string.Empty;
        public int Score { get; set; }

        public int Letters => string.IsNullOrWhiteSpace(Name) ? 0 : Name.Length;
    }
}
