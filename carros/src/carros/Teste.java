package carros;
import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Veiculo[] veiculos = new Veiculo[5];

        for (int i = 0; i < 5; i++) {
            System.out.println("=== Cadastro do Veículo " + (i + 1) + " ===");

            Veiculo veiculo = new Veiculo();

            System.out.print("Placa: ");
            veiculo.setPlaca(input.nextLine());

            System.out.print("Marca: ");
            veiculo.setMarca(input.nextLine());

            System.out.print("Modelo: ");
            veiculo.setModelo(input.nextLine());

            System.out.print("Cor: ");
            veiculo.setCor(input.nextLine());

            System.out.print("Velocidade Máxima: ");
            veiculo.setVelocMax(input.nextInt());

            System.out.print("Quantidade de Rodas: ");
            veiculo.setQtdRodas(input.nextInt());

            System.out.println("=== Cadastro do Motor do Veículo " + (i + 1) + " ===");

            Motor motor = new Motor();

            System.out.print("Quantidade de Pistões: ");
            motor.setQtdPist(input.nextInt());

            System.out.print("Potência: ");
            motor.setPotencia(input.nextInt());

            veiculo.setMotor(motor);

            input.nextLine();

            veiculos[i] = veiculo;
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("=== Veículo " + (i + 1) + " ===");
            System.out.println("Placa: " + veiculos[i].getPlaca());
            System.out.println("Marca: " + veiculos[i].getMarca());
            System.out.println("Modelo: " + veiculos[i].getModelo());
            System.out.println("Cor: " + veiculos[i].getCor());
            System.out.println("Velocidade Máxima: " + veiculos[i].getVelocMax());
            System.out.println("Quantidade de Rodas: " + veiculos[i].getQtdRodas());
            System.out.println("=== Motor do Veículo " + (i + 1) + " ===");
            System.out.println("Quantidade de Pistões: " + veiculos[i].getMotor().getQtdPist());
            System.out.println("Potência: " + veiculos[i].getMotor().getPotencia());
            System.out.println("=====================================");
        }
    }
}