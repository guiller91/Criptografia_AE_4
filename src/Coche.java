import java.io.Serializable;

public class Coche implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String matricula;
	private String marca;
	private String modelo;
	private int precio;
	
	
	public Coche() {
		super();
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}


	@Override
	public String toString() {
		return "Coche [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", precio=" + precio + "]";
	}
	
	
}
