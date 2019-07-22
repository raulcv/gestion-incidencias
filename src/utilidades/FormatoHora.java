package utilidades;

public class FormatoHora {

	public Boolean validarhora(String hora){
		boolean b;
		char[] a = hora.toString().toCharArray();
		String[]c = hora.split(":");		
		if((a[0] == ' ') || (a[1] == ' ') || (a[2] == ' ') || (a[3] == ' ') || (a[4] == ' ') || (a[5] == ' ') || (a[6] == ' ') || (a[7] == ' ')  
				|| (getInteger(c[0]) > 24) || (getInteger(c[1]) > 59 ) || (getInteger(c[2]) > 59 )){			
			b = false;
		}else{
			b = true;
		}		
		return b;
	}
	public int getInteger(String valor){
		int integer = Integer.parseInt(valor);
		return integer;
	}
}
