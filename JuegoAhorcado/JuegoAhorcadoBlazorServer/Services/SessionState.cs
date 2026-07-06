using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class SessionState
    {
        public Player? CurrentUser { get; private set; }
        public bool IsLoggedIn => CurrentUser != null;

        public void Login(Player player)
        {
            CurrentUser = player;
        }

        public void Logout()
        {
            CurrentUser = null;
        }
    }
}
