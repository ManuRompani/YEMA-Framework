namespace JuegoAhorcadoBlazorServer.Modelo
{
    public class BackendResponse
    {
        public bool Success { get; set; }
        public bool IsRepeated { get; set; }
        public bool IsUnauthorized { get; set; }
        public string Message { get; set; } = "";

        public static BackendResponse Ok()
        {
            return new BackendResponse
            {
                Success = true,
                IsRepeated = false,
                IsUnauthorized = false,
                Message = "Operación realizada correctamente."
            };
        }

        public static BackendResponse Repeated()
        {
            return new BackendResponse
            {
                Success = false,
                IsRepeated = true,
                IsUnauthorized = false,
                Message = "La palabra ya existe."
            };
        }

        public static BackendResponse Unauthorized()
        {
            return new BackendResponse
            {
                Success = false,
                IsRepeated = false,
                IsUnauthorized = true,
                Message = "No tenés permisos para realizar esta acción."
            };
        }

        public static BackendResponse Bad(string message)
        {
            return new BackendResponse
            {
                Success = false,
                IsRepeated = false,
                IsUnauthorized = false,
                Message = string.IsNullOrWhiteSpace(message)
                    ? "Ocurrió un error."
                    : message
            };
        }
    }
}
