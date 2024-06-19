
using SpaceHabitFrontier.Services;
namespace SpaceHabitFrontier;

public class Program
{
	public static void Main(string[] args)
	{
		var builder = WebApplication.CreateBuilder(args);

		var corsPolicyName = "spf_cors_policy";
		// Add services to the container.

		builder.Services.AddScoped<LookupsService, LookupsService>();

		builder.Services.AddControllers();
		builder.Services.AddCors(options => {
			options.AddPolicy(
				name: corsPolicyName,
				policy => {
					policy.WithOrigins(
						"http://localhost:3000",
						"https://localhost:3000"
					);
					policy.AllowAnyMethod();
					policy.WithHeaders(
						"Authorization",
						"Cookie"
					);
					policy.WithExposedHeaders("x-authexpired");
					policy.AllowCredentials();
				}
			);
		});


		// Learn more about configuring Swagger/OpenAPI 
		// at https://aka.ms/aspnetcore/swashbuckle
		builder.Services.AddEndpointsApiExplorer();
		builder.Services.AddSwaggerGen();

		var app = builder.Build();
		app.UseCors(corsPolicyName);

		// Configure the HTTP request pipeline.
		if (app.Environment.IsDevelopment())
		{
			app.UseSwagger();
			app.UseSwaggerUI();
		}

		// app.UseHttpsRedirection();

		app.UseAuthorization();


		app.MapControllers();

		app.Run();
	}
}
