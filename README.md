App do desafio da empresa ioasys (https://bitbucket.org/ioasys/empresas-android/src/master/). Composto por 3 telas:

1. Tela de login: Foram tratados os casos de erro em que o email e a senha foram inválidos. Foi utilizado o padrão OAuth 2.0 e no caso de sucesso do login a api retornou 3 custom headers (access-token, uid, client). Usuário: testeapple@ioasys.com.br / Senha: 12341234;

2. Tela de listagem das empresas: Nessa tela foi feita uma requisição para obter a lista de empresas e utilizado uma endpoint para filtrar a mesma. Foram utilizados os headers obtidos da tela anterior para autenticar essa requisição;

3. Tela de descrição de uma empresa: Nessa tela são exibidos em detalhe as informações de uma empresa clicada na lista da tela anterior.

Principais bibliotecas utilizadas:

- Gerenciamento de estado: ViewModel, Coroutines
- Requisições api REST: Retrofit
- Download de imagens: Glide
- Injeção de dependência: Koin

<p align="ligth">
  <img src="https://user-images.githubusercontent.com/99301401/164817633-128ed6cd-de60-42a4-a09b-00d3efef4768.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/164817637-2719355a-47cb-4771-93bb-011ae36edd6f.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/164817639-0066d5dc-71b7-467c-989e-4481acce8c90.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/164817641-e485c828-a628-4947-aba4-7ccbd4618bf2.png" width="250px"/>
</p>
