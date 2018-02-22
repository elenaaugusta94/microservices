public aspect Aspect {

	public pointcut executionGetForObject():
		execution(* postForObject(..)) ;
	
	
	String around(): executionGetForObject(){
		Object [] args = thisJoinPoint.getArgs();
		String s0 = (String) args[0];
	//	System.out.println("S0: " + s0);
		String[] string = s0.split("//");
		String[] name = string[1].split("/");
		String retorno = name[0];
		//System.out.println("Name: " + name[0]);
		String c = "";
		//System.out.println(name.length);
		for( int i=1 ; i<name.length;i++) {
		//	System.out.println("name: " + name[i]);
			if(!name[i].contains("?")&&(!name[i].contains("&"))) {
			//	System.out.println("nao contains!!");
				c+="/"+name[i]+"/";
			}
			else if(name[i].contains("?")){
				String aux[] = name[i].split("\\?");
				c+=aux[0];
			}else if(name[i].contains("&")) {
				String aux[] = name[i].split("&");
				c+= "/"+aux[0];
			}
				
		}
		retorno +=  " using " + c;
		System.out.println(retorno);
		proceed();
		return retorno;
	}	

	
//	after(): executionGetForObject(){
//		System.out.println("Achou o metodo! ");
//	}
	
	
//	pointcut field(): 
//		get (teste.Teste2 Teste*);
//	
//	after(): field(){
//		System.out.println("Achou o campo! ");
//	}
		
	
//	pointcut methodPost(): 
//		call (void teste.AspectTeste.getAuthentication(..));
//	
//	after(): methodPost(){
//		System.out.println("Achou o post! ");
//	}
}
