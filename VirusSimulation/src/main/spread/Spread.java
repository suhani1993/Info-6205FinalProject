package main.spread;

public abstract class Spread {

	private Double R;
	private Double K;

	public Double getR() {
		return R;
	}

	public void setR(Double r) {
		R = r;
	}

	public Double getK() {
		return K;
	}

	public void setK(Double k) {
		K = k;
	}

	@Override
	public String toString() {
		return "Spread [R=" + R + ", K=" + K + "]";
	}

}
