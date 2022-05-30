App do desafio da empresa ioasys (https://bitbucket.org/ioasys/empresas-android/src/master/). Composto por 3 telas:

1. Tela de login: Foram tratados os casos de erro em que o email e a senha foram inválidos. Foi utilizado o padrão OAuth 2.0 e no caso de sucesso do login a api retornou 3 custom headers (access-token, uid, client). Usuário: testeapple@ioasys.com.br / Senha: 12341234;

2. Tela de listagem das empresas: Nessa tela foi feita uma requisição para obter a lista de empresas e utilizado uma endpoint para filtrar a mesma. Foram utilizados os headers obtidos da tela anterior para autenticar essa requisição;

3. Tela de descrição de uma empresa: Nessa tela são exibidos em detalhe as informações de uma empresa clicada na lista da tela anterior.

#

Principais bibliotecas utilizadas:

- Gerenciamento de estado: ViewModel, Coroutines
- Requisições api REST: Retrofit
- Download de imagens: Glide
- Injeção de dependência: Koin
- Testes unitários: JUnit4 e Mockk

#

<p align="ligth">
  <img src="https://user-images.githubusercontent.com/99301401/170917952-a513c54b-7670-4f8f-b513-724919a910d7.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918506-e9537790-442d-4abc-982c-514ea31187cb.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918608-af4126fd-50fc-4701-93c8-a63a3a7d787c.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918799-a6eb2000-5042-4b5a-950a-ff4b273f7def.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918878-6a744daa-d75e-4d7d-9680-649ce0cce088.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918892-1b35dcae-8d6f-42e4-b3f8-50cf667d0551.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918909-19859c3b-17fb-4baa-a04f-4a68112052d9.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918934-85705405-dd43-47aa-996a-da7881c4d0f2.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170918950-2b83de8c-0e5c-42ec-9062-8cc74861d7be.png" width="250px"/>
  <img src="https://user-images.githubusercontent.com/99301401/170919291-5cda8afc-d4bf-4e11-adc9-15e8be266fb9.gif" width="250px"/>
</p>
