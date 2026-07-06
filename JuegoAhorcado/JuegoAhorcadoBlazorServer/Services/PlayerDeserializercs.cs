using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class PlayerDeserializer
    {
        public Player? Deserialize(string text)
        {
            if(text == "null")
            {
                return null;
            }
            string[] values = text.Split('|');

            Player player = new Player
            {
                Id = int.Parse(values[0]),
                Name = values[1],
                Pass = values[2],
                Role = values[3],
                Rounds = int.Parse(values[4]),
                Points = int.Parse(values[5])
            };

            return player;
        }
    }
}
