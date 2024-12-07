package DTOs;

import Enum.Intensidade;
import Enum.NivelDificuldade;
import Enum.TipoTreino;

import java.time.LocalDate;


public class TreinoTO {
    private int id;                     // ID único do treino
    private int alunoId;                // ID do aluno relacionado
    private TipoTreino tipoTreino;      // Enum para o foco do treino
    private Intensidade intensidade;    // Enum para intensidade do treino
    private LocalDate data;             // Data do treino
    private NivelDificuldade nivelDificuldade; // Enum para nível de dificuldade

    // Construtor vazio
    public TreinoTO() {}

    // Construtor completo
    public TreinoTO(int id, int alunoId, TipoTreino tipoTreino, Intensidade intensidade, LocalDate data, NivelDificuldade nivelDificuldade) {
        this.id = id;
        this.alunoId = alunoId;
        this.tipoTreino = tipoTreino;
        this.intensidade = intensidade;
        this.data = data;
        this.nivelDificuldade = nivelDificuldade;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public TipoTreino getTipoTreino() {
        return tipoTreino;
    }

    public void setTipoTreino(TipoTreino tipoTreino) {
        this.tipoTreino = tipoTreino;
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
        return nivelDificuldade;
    }

    public void setNivelDificuldade(NivelDificuldade nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }

    @Override
    public String toString() {
        return "Treino{" +
               "id=" + id +
               ", alunoId=" + alunoId +
               ", tipoTreino=" + tipoTreino +
               ", intensidade=" + intensidade +
               ", data=" + data +
               ", nivelDificuldade=" + nivelDificuldade +
               '}';
    }
}
