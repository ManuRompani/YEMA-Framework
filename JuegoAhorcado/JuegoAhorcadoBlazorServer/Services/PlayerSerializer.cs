using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class PlayerSerializer
    {
        public string Serialize(Player player)
        {
            string values = "";

            values += player.Id;
            values += "%" + player.Name;
            values += "%" + player.Pass;
            values += "%" + player.Role;
            values += "%" + player.Rounds;
            values += "%" + player.Points;

            return values;
        }
    }
}
