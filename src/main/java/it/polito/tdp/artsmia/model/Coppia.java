package it.polito.tdp.artsmia.model;

public class Coppia {
		Integer a1;
		Integer a2;
		Integer peso;
		/**
		 * @param a1
		 * @param a2
		 * @param peso
		 */
		public Coppia(Integer a1, Integer a2, Integer peso) {
			super();
			this.a1 = a1;
			this.a2 = a2;
			this.peso = peso;
		}
		public Integer getA1() {
			return a1;
		}
		public void setA1(Integer a1) {
			this.a1 = a1;
		}
		public Integer getA2() {
			return a2;
		}
		public void setA2(Integer a2) {
			this.a2 = a2;
		}
		public Integer getPeso() {
			return peso;
		}
		public void setPeso(Integer peso) {
			this.peso = peso;
		}
		@Override
		public String toString() {
			return "Coppia " + a1 + " " + a2 + " - peso:" + peso;
		}
		
}
