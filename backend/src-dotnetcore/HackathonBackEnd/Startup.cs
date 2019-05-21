using HackathonBackEnd.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authentication.OpenIdConnect;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Authorization;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Sbb.Rsu.AzureAD.Client.DependencyInjection;
using Sbb.Rsu.AzureAD.Server;
using Sbb.Rsu.Core;
using Swashbuckle.AspNetCore.Swagger;
using System;
using System.IO;

namespace HackathonBackEnd
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddAuthentication()
                .AddSbbOpenIdConnect("https://graph.microsoft.com")
                .AddSbbJwtBearer();

            services.AddAuthorization(options =>
            {
                options.AddPolicy("Application", policyBuilder => policyBuilder
                    .RequireClaim("appId"));

                options.AddPolicy("AuthenticatedAllSchemes", policyBuilder => policyBuilder
                    .AddAuthenticationSchemes(JwtBearerDefaults.AuthenticationScheme, OpenIdConnectDefaults.AuthenticationScheme)
                    .RequireAuthenticatedUser());
            });

            // Authenticate all pages
            services.AddMvc(options => options.Filters.Add(new AuthorizeFilter("AuthenticatedAllSchemes")))
                .SetCompatibilityVersion(CompatibilityVersion.Version_2_2);

            services.AddSwaggerGen(options =>
            {
                options.SwaggerDoc("v1", new Info
                {
                    Title = "SB ÖBB SBB Hackaton API",
                });

                var xmlCommentsFilePath = Path.Combine(AppContext.BaseDirectory, $"{typeof(Startup).Assembly.GetName().Name}.xml");
                options.IncludeXmlComments(xmlCommentsFilePath);
            });

            services
                .AddTenantInformation(Configuration)
                .AddAppInformation(Configuration)
                .AddDelegatedTokenAcquirer()
                .AddSingleton<IHttpContextAccessor, HttpContextAccessor>()
                .AddScoped<GraphService>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            app.UseAuthentication();

            app.UseCors(builder =>
                builder.WithOrigins("https://localhost:8000")
                    .AllowAnyHeader()
                    .AllowAnyMethod()
                    .AllowCredentials());

            app.UseSwagger();
            app.UseSwaggerUI(options => options.SwaggerEndpoint("/swagger/v1/swagger.json", "v1"));

            app.UseHttpsRedirection();
            app.UseMvc();
        }
    }
}
