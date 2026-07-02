namespace JuegoAhorcadoBlazorServer.Modelo
{
    public class BackendResponse
    {
        public bool Success { get; set; }
        public bool IsRepeated { get; set; }
        public string Message { get; set; } = "";

        public static BackendResponse Ok()
        {
            return new BackendResponse
            {
                Success = true,
                IsRepeated = false,
                Message = "Operación realizada correctamente."
            };
        }

        public static BackendResponse Repeated()
        {
            return new BackendResponse
            {
                Success = false,
                IsRepeated = true,
                Message = "La palabra ya existe."
            };
        }

        public static BackendResponse Bad(string message)
        {
            return new BackendResponse
            {
                Success = false,
                IsRepeated = false,
                Message = string.IsNullOrWhiteSpace(message)
                    ? "Ocurrió un error."
                    : message
            };
        }
    }
}
