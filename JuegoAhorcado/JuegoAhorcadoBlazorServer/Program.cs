using JuegoAhorcadoBlazorServer.Components;
using JuegoAhorcadoBlazorServer.Negocio;
using JuegoAhorcadoBlazorServer.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddSingleton(_ => new Comunicador());
builder.Services.AddScoped<PlayerNegocio>();
builder.Services.AddScoped<WordNegocio>();
builder.Services.AddScoped<ScoreNegocio>();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseAntiforgery();
app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
