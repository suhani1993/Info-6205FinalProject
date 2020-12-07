package main.symptom;

public class Symptom {
	
	private Level level;
	private SymptomEnum symptom;
	
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public SymptomEnum getSymptom() {
		return symptom;
	}

	public void setSymptom(SymptomEnum symptom) {
		this.symptom = symptom;
	}

	public enum SymptomEnum{
		COUGH("Cough"),
		FEVER("Fever"),
		HEADACHE("Headache");
		
		private String nextSymptom;
		
		private SymptomEnum(String nextSymptom) {
			this.nextSymptom = nextSymptom;
		}

		public String getNextSymptom() {
			return nextSymptom;
		}

		public void setNextSymptom(String nextSymptom) {
			this.nextSymptom = nextSymptom;
		}
		
		@Override
        public String toString() {
            return nextSymptom;
        }
	}
	
	public enum Level{
		Mild("Mild"),
		Moderate("Moderate"),
		High("High");
		
		private String level;
		
		private Level(String level) {
			this.level = level;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}
		
		@Override
        public String toString() {
            return level;
        }
	}
}
