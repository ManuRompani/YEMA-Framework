using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class PlayerSerializer
    {
        public string Serialize(Player player)
        {
            if (player == null)
                throw new ArgumentNullException(nameof(player));

            if (player.Role == null)
                throw new Exception("El Player debe tener un Role asignado.");

            return string.Join("|",
                player.Id,
                player.Name,
                player.Password,
                player.Role.Id,
                player.Role.Name,
                player.Rounds,
                player.Points
            );
        }
    }
}
