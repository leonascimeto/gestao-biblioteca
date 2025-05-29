package lendev.library.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Classe de configuração para customizar a documentação da API com o Swagger/OpenAPI
@Configuration
public class SwaggerConfig {

    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Biblioteca Lendev") 
                .version("1.0") 
                .description("Documentação da API para gerenciamento de livros, alunos e empréstimos")); 
    }
}
