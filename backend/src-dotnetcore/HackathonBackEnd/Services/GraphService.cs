using Newtonsoft.Json.Linq;
using Sbb.Rsu.AzureAD.Client;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace HackathonBackEnd.Services
{
    public class GraphService
    {
        private static readonly HttpClient HttpClient = new HttpClient();
        private readonly IDelegatedTokenAcquirer delegatedTokenAcquirer;

        public GraphService(IDelegatedTokenAcquirer delegatedTokenAcquirer)
        {
            this.delegatedTokenAcquirer = delegatedTokenAcquirer;
        }

        public async Task<User> GetUser() => await new HttpRequestBuilder(HttpClient)
            .SetMethod(HttpMethod.Get)
            .Authenticate(delegatedTokenAcquirer, "https://graph.microsoft.com")
            .SetUrl("https://graph.microsoft.com/v1.0/me")
            .JsonResponse<User>()
            .ExecuteFunc<User>();

        public async Task<IEnumerable<UserListItem>> GetUserListItems() => await new HttpRequestBuilder(HttpClient)
            .SetMethod(HttpMethod.Get)
            .Authenticate(delegatedTokenAcquirer, "https://graph.microsoft.com")
            .SetUrl("https://graph.microsoft.com/v1.0/sites/devsbb.sharepoint.com,f773671a-ff6d-4868-aff2-bdddd5bdcf6b,7bcbb4e7-6e83-4309-a9b7-90220a30c880/lists/615020ab-ed5f-4960-8e1a-b00a0b08c9eb/items?expand=fields(select=Title,Skills,Projects,Organisation,Ausbildung)")
            .JsonResponse(parseJObject: obj => obj["value"].Select(item => GetUserListItem(item["fields"])))
            .ExecuteFunc<IEnumerable<UserListItem>>();

        private UserListItem GetUserListItem(JToken item)
        {
            var name = item["Title"].ToObject<string>();
            return new UserListItem
            {
                Name = name,
                Rank = ((double)name.GetHashCode() / int.MaxValue + 1) * 50,
                Image = $"{name}.jpg",
                Education = item["Ausbildung"].ToObject<string>(),
                Company = item["Organisation"].ToObject<string>(),
                Skills = item["Skills"].ToObject<string>().Split("\n"),
                Projects = item["Projects"].ToObject<string>().Split("\n"),
                BearbeiteteDokumente = new string[] { },

                // Title,Skills,Projects,Ausbildung
            };
        }
    }
}