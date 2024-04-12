package steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;

import core.BaseTest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ViagemSteps extends BaseTest {
	
	private static String TOKEN;
	private static Response RESPONSE;
	
	@BeforeClass
	public static void token() {
		
//		RestAssured.requestSpecification.header("Authorization", "Bearer "+token);
	}
	
	public Map<String, String> dadosParaCadastroDeViagem(String nome, String dataDePartida, String dataDeRetorno, String localDestino, String regiao) {
		Map<String, String> viagem = new HashMap<String, String>();
	    viagem.put("acompanhante", nome);
	    viagem.put("dataPartida", dataDePartida);
	    viagem.put("dataRetorno", dataDeRetorno);
	    viagem.put("localDeDestino", localDestino);
	    viagem.put("regiao", regiao);
	    
	    return viagem;
	}
	
	@Given("que o usuario realize o login {string} {string}")
	public void que_o_usuario_realize_o_login(String usuario, String senha) {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", usuario);
		login.put("senha", senha);
		
			TOKEN = given()
				.log().all()	
				.contentType(ContentType.JSON)
				.body(login)
			.when()
				.post("http://localhost:8089/api/v1/auth")
			.then()
				.log().all()
				.statusCode(200)
				.extract().path("data.token").toString()
			;
	}

	@When("cadastrar um livro com os dados acompanhante {string} dataPartida {string} dataRetorno {string} localDeDestino {string} regiao {string} no endpoint {string}")
	public void cadastrar_um_livro_com_os_dados_acompanhante_dataPartida_dataRetorno_localDeDestino_regiao_no_endpoint(String acompanhante, String dataPartida, String dataRetorno, String localDeDestino, String regiao, String endpoint) {
		RESPONSE = given()
	            .log().all()
	            .header("Authorization", "Bearer "+TOKEN)
	            .contentType(ContentType.JSON)
	            .body(dadosParaCadastroDeViagem(acompanhante, dataPartida, dataRetorno, localDeDestino, regiao))
	        .when()
	            .post("http://localhost:8089/api"+endpoint)
	        .then()
	            .log().all()
	            .extract().response(); 
		
		assertEquals(RESPONSE.path("data.acompanhante"), acompanhante);
	    assertEquals(RESPONSE.path("data.localDeDestino"), localDeDestino);
	    assertTrue(RESPONSE.asString().contains(regiao));
	}

	@Then("validar cadastro concluido com sucesso statuscode {int}")
	public void validar_cadastro_concluido_com_sucesso_statuscode(int statuscode) {
		assertEquals(statuscode, RESPONSE.statusCode());
		
	}
	
	@When("editar dados da viagem {string} {string} {string} {string} {string}")
	public void editar_dados_da_viagem(String acompanhante, String dataPartida, String dataRetorno, String localDeDestino, String regiao) {
		RESPONSE = given()
				.log().all()
				.header("Authorization", "Bearer "+TOKEN)
	            .contentType(ContentType.JSON)
	            .body(dadosParaCadastroDeViagem(acompanhante, dataPartida, dataRetorno, localDeDestino, regiao))
			.when()
				.put("http://localhost:8089/api/v1/viagens/1")
			.then()
				.log().all()
				.extract().response()
			;
		
	}

	@Then("valido viagem editada com sucesso {int}")
	public void valido_viagem_editada_com_sucesso(int statuscode) {
		assertEquals(204, RESPONSE.statusCode());
	}
	
	@When("excluir viagem {int}")
	public void excluir_viagem(int id) {
			RESPONSE = given()
				.log().all()
				.header("Authorization", "Bearer "+TOKEN)
				.contentType(ContentType.JSON)
			.when()
				.delete("http://localhost:8089/api/v1/viagens/"+ id)
			.then()
				.log().all()
				.extract().response()
				;
	}

	@Then("validar viagem excluida com sucesso {int}")
	public void validar_viagem_excluida_com_sucesso(int statuscode) {
	    assertEquals(statuscode, RESPONSE.statusCode());
	}
	
	@When("pego status da aplicacao")
	public void pego_status_da_aplicacao() {
		RESPONSE = given()
				.log().all()
				.header("Authorization", "Bearer "+TOKEN)
				.contentType(ContentType.JSON)
			.when()
				.get("http://localhost:8089/api/v1/status")
			.then()
				.log().all()
				.extract().response()
				;
	}

	@Then("validar mensagem de sucesso {string}")
	public void validar_mensagem_de_sucesso(String mensagem) {
	    assertEquals(mensagem, RESPONSE.asString());
	    assertEquals(200, RESPONSE.statusCode());
	}
	
	@When("pegar lista de viagens da regiao {string}")
	public void pegar_lista_de_viagens(String regiao) {
		RESPONSE = given()
	            .log().all()
	            .header("Authorization", "Bearer "+TOKEN)
	            .queryParam("regiao", regiao)
	        .when()
	            .get("http://localhost:8089/api/v1/viagens")
	        .then()
	            .log().all()
	            .extract().response()
	           ;
		
	}

	@SuppressWarnings("deprecation")
	@Then("validar quantidade de linhas {string}")
	public void validar_mensagem_de_sucesso1(String quantidadeDeLinhas) {
//	    assertEquals("quantidadeDeLinhas", RESPONSE.path("data.size()").toString());
		assertThat(RESPONSE.path("data"), notNullValue());
	}
	
}
