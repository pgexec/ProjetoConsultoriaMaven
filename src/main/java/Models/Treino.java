package Models;

import Enum.Intensidade;
import Enum.NivelDificuldade;
import Enum.TipoTreino;

import java.time.LocalDate;


public class Treino {
	private int id;                     // ID único do treino
    private int aluno_id;                // ID do aluno relacionado
    private TipoTreino tipo_treino;      // Enum para o foco do treino
    private Intensidade intensidade;    // Enum para intensidade do treino
    private LocalDate data;             // Data do treino
    private NivelDificuldade nivel_dificuldade; // Enum para nível de dificuldade

    // Construtor vazio
    public void TreinoTO() {}

    // Construtor completo
    public void TreinoTO(int id, int aluno_id, TipoTreino tipo_treino, Intensidade intensidade, LocalDate data, NivelDificuldade nivel_dificuldade) {
        this.id = id;
        this.aluno_id = aluno_id;
        this.tipo_treino = tipo_treino;
        this.intensidade = intensidade;
        this.data = data;
        this.nivel_dificuldade = nivel_dificuldade;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return aluno_id;
    }

    public void setAlunoId(int alunoId) {
        this.aluno_id = alunoId;
    }

    public TipoTreino getTipoTreino() {
        return tipo_treino;
    }

    public void setTipoTreino(TipoTreino tipoTreino) {
        this.tipo_treino = tipoTreino;
    }

    public Intensidade getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(Intensidade intensidade) {
        this.intensidade = intensidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public NivelDificuldade getNivelDificuldade() {
        return nivel_dificuldade;
    }

    public void setNivelDificuldade(NivelDificuldade nivelDificuldade) {
        this.nivel_dificuldade = nivelDificuldade;
    }

    @Override
    public String toString() {
        return "Treino{" +
               "id=" + id +
               ", alunoId=" + aluno_id +
               ", tipoTreino=" + tipo_treino +
               ", intensidade=" + intensidade +
               ", data=" + data +
               ", nivelDificuldade=" + nivel_dificuldade +
               '}';
    }
}
