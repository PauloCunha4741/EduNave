package com.cesar.edunave.usuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.Diretorio;
import com.cesar.edunave.enums.TipoAcesso;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Estudante extends Usuario {

    public Estudante() {
        super();
    }

    public Estudante(String nome, String email, String turma) {
        super(nome, email, TipoAcesso.Estudante.name(), turma);
    }

    private static final String ELETIVA_JSON_FILE_PATH = Diretorio.EletivaJson.getCaminho();

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.getId());
        jsonObject.put("nome", this.getNome());
        jsonObject.put("email", this.getEmail());
        jsonObject.put("senha", this.getSenha());
        jsonObject.put("tipoAcesso", this.getTipoAcesso());
        jsonObject.put("turma", this.getTurma());
        return jsonObject;
    }

    public List<Eletiva> verificarEletivasDisponiveis() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Eletiva> todasEletivas = new ArrayList<>();
        List<Eletiva> eletivasdisponiveis = new ArrayList<>();

        try {
            todasEletivas = objectMapper.readValue(new File(ELETIVA_JSON_FILE_PATH), new TypeReference<List<Eletiva>>(){});
            
            for (Eletiva eletiva : todasEletivas) {
                if (!eletiva.getBloqueada() && eletiva.getTurma().equals(this.getTurma())) {
                    eletivasdisponiveis.add(eletiva);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eletivasdisponiveis;
    }

    public boolean inscreverNaEletiva(String titulo) {
      try {
            String content = new String(Files.readAllBytes(Paths.get(ELETIVA_JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eletiva = jsonArray.getJSONObject(i);
                if (eletiva.getString("titulo").equals(titulo)) {
                    if (eletiva.getBoolean("bloqueada")) {
                        System.out.println("Número máximo de estudantes atingido. A eletiva está bloqueada.");
                        return false;
                    }

                    if (verificarInscricaoEmEletiva(titulo)) {
                        System.out.println("O estudante já se cadastrou na eletiva selecionada.");
                        return false;
                    }

                    if (this.getTurma().equals("SegundoAno") && contarEmailEstudantes() == 2) {
                        System.out.println("O estudante já se cadastrou no máximo possível de eletivas.");
                        return false;
                    } else if (!this.getTurma().equals("SegundoAno") && contarEmailEstudantes() == 1 ) {
                        System.out.println("O estudante já se cadastrou no máximo possível de eletivas.");
                        return false;
                    }

                    JSONArray estudantesCadastrados = eletiva.getJSONArray("estudantesCadastrados");

                    if (estudantesCadastrados.length() == 44) {
                        eletiva.put("bloqueada", true);
                    }

                    estudantesCadastrados.put(this.toJSONObject());
                    Files.write(Paths.get(ELETIVA_JSON_FILE_PATH), jsonArray.toString(4).getBytes());
                    System.out.println("Estudante inscrito na eletiva com sucesso.");
                    return true;
                }
            }

            System.out.println("Eletiva com o título especificado não encontrada.");
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

     private int contarEmailEstudantes() {
        ObjectMapper objectMapper = new ObjectMapper();
        int count = 0;

        try {
            List<Map<String, Object>> todasEletivas = objectMapper.readValue(new File(ELETIVA_JSON_FILE_PATH), new TypeReference<List<Map<String, Object>>>() {});
            
            for (Map<String, Object> eletiva : todasEletivas) {
                List<Map<String, String>> estudantesCadastrados = (List<Map<String, String>>) eletiva.get("estudantesCadastrados");
                
                for (Map<String, String> estudante : estudantesCadastrados) {
                    if (this.getEmail().equals(estudante.get("email"))) {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    private boolean verificarInscricaoEmEletiva(String titulo) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, Object>> todasEletivas = objectMapper.readValue(new File(ELETIVA_JSON_FILE_PATH), new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> eletiva : todasEletivas) {
                if (eletiva.get("titulo").equals(titulo)) {
                    List<Map<String, String>> estudantesCadastrados = (List<Map<String, String>>) eletiva.get("estudantesCadastrados");

                    for (Map<String, String> estudante : estudantesCadastrados) {
                        if (this.getEmail().equals(estudante.get("email"))) {
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estudante estudante = (Estudante) obj;
        return this.getEmail().equals(estudante.getEmail()); // Exemplo de comparação, ajuste conforme necessário
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail()); // Correspondente ao método equals
    }
}
