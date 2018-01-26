//package teste;
//
//public class AspectTeste {
//
//	public static void main(String[] args) {
//
//		String [] pathClass  = args;
//				
//		AspectTeste asp = new AspectTeste();
//		
//		Teste2 t = new Teste2();
//		asp.getAuthentication("elena", "123");
//		asp.productUpdate(2, "3");
//	
//	}
//	public void methodOne(String s) {
//		System.out.println("S: " + s);
//	}
//	public void getAuthentication(String user,  String senha) {
//		Teste2 restTemplate = new Teste2();
//		String info = "http://MsAuthentication/api/authenticate?username=" + user + "&password=" + senha;
//		String response = restTemplate.postForObject(info);
//		//return response;
//
//	}
//	public String productUpdate(int qnt, String id){
//		Teste2 restTemplate = new Teste2();
//		String response = restTemplate.postForObject(
//				"http://MsProduct/updateProduct&qnt=" + qnt + "&id=" + id);
//		return response;
//	}
//}
