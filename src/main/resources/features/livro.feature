#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Livraria

  @01
  Scenario: Realizar login em viagens
    Given que o usuario realize o login "admin@email.com" "654321"
    When cadastrar um livro com os dados acompanhante "Patrick" dataPartida "2024-10-04" dataRetorno "2024-10-05" localDeDestino "Fortaleza" regiao "Norte" no endpoint "/v1/viagens"
    Then validar cadastro concluido com sucesso statuscode 201
    
	
	@02
	Scenario Outline: Editar viagem
		Given que o usuario realize o login "admin@email.com" "654321"
		When editar dados da viagem "<acompanhante>" "<dataPartida>" "<dataRetorno>" "<localDeDestino>" "<regiao>"
		Then valido viagem editada com sucesso <status>
	
	 Examples: 
      | acompanhante |dataPartida |dataRetorno|localDeDestino|regiao |status  |
      | Gabriela		 |2024-10-04  |2024-10-05 |Brasilia			 |Centro |204     |
      
      
  @03
  Scenario Outline: Excluir viagem
    Given que o usuario realize o login "admin@email.com" "654321"
    When excluir viagem <id>
    Then validar viagem excluida com sucesso <statuscode>

    Examples: 
      |id |statuscode|
      |02 |204 			 |
      
  @04
  Scenario Outline: Pegar status do serviço
    Given que o usuario realize o login "admin@email.com" "654321"
    When pego status da aplicacao
    Then validar mensagem de sucesso "<mensagem>"

    Examples: 
      |mensagem																 |
      |Aplicação está funcionando corretamente |
      
  @05
  Scenario Outline: Validar lista de viagens
    Given que o usuario realize o login "usuario@email.com" "123456"
    When pegar lista de viagens da regiao "<regiao>"
    Then validar quantidade de linhas  "<linhas>"

    Examples: 
      |linhas | regiao |
      |9      | Norte  |
      
      
