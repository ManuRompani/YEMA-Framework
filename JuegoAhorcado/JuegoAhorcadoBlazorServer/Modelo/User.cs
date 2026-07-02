namespace JuegoAhorcadoBlazorServer.Modelo
{
    public class User
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Pass { get; set; }
        public string Role { get; set; } = "player";
    }
}
