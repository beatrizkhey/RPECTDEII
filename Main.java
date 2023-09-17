import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Stack<Integer>> piles = new ArrayList<>(3);

        for (int i = 0; i < 3; i++) {
            piles.add(new Stack<>());
        }

        System.out.print("Digite a quantidade de números na primeira pilha: ");
        int numNumbers = scanner.nextInt();

        Random random = new Random();
        List<Integer> randomNumbers = new ArrayList<>();

        for (int i = 0; i < numNumbers; i++) {
            randomNumbers.add(random.nextInt(100) + 1);
        }

        // Ordene aleatoriamente os números em ordem decrescente
        Collections.sort(randomNumbers, Collections.reverseOrder());

        piles.get(0).addAll(randomNumbers);

        int moves = 0;
        boolean couldDoBetter = false; // Verifica se o jogador poderia fazer melhor

        System.out.println("Pilhas iniciais:");
        printPiles(piles);

        int minimumMoves = (int) Math.pow(2, numNumbers) - 1;

        boolean gameComplete = false; // Verifica se o jogo foi concluído

        while (!gameComplete) {
            System.out.println("\nMenu de opções:");
            System.out.println("0 - Sair do jogo");
            System.out.println("1 - Movimentar");
            System.out.println("2 - Solução automática");

            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                while (true) {
                    System.out.print("Digite a pilha de origem (1, 2, 3) ou 0 para voltar ao menu principal: ");
                    int source = scanner.nextInt() - 1;

                    if (source == -1) {
                        break;
                    }

                    System.out.print("Digite a pilha de destino (1, 2, 3) ou 0 para voltar ao menu principal: ");
                    int dest = scanner.nextInt() - 1;

                    if (dest == -1) {
                        break;
                    }

                    if (isValidPile(source, 3) && isValidPile(dest, 3) && source != dest) {
                        if (!piles.get(source).isEmpty()) {
                            int value = piles.get(source).peek();

                            if (piles.get(dest).isEmpty() || value < piles.get(dest).peek()) {
                                piles.get(source).pop();
                                piles.get(dest).push(value);
                                moves++;
                                printPiles(piles);

                                if (piles.get(0).isEmpty() && piles.get(1).isEmpty() && !couldDoBetter) {
                                    System.out.println("Ordenação concluída em " + moves + " jogadas.");
                                    checkSolution(moves, minimumMoves);
                                    gameComplete = true; // O jogo foi concluído
                                    break;
                                }
                            } else {
                                System.out.println("Movimento inválido. O número só pode ser movido para uma pilha com topo maior.");
                            }
                        } else {
                            System.out.println("A pilha de origem está vazia.");
                        }
                    } else {
                        System.out.println("Escolha pilhas válidas.");
                    }
                }
            } else if (choice == 2) {
                solucaoAutomatica(piles, numNumbers);
                moves += minimumMoves; // Adicione o número mínimo de movimentos da solução automática
                printPiles(piles);
                System.out.println("Ordenação concluída em " + moves + " jogadas.");
                checkSolution(moves, minimumMoves);
                gameComplete = true; // O jogo foi concluído
                break;
            } else {
                System.out.println("Escolha uma opção válida.");
            }
        }

        scanner.close();
    }

    public static void printPiles(List<Stack<Integer>> piles) {
        for (int i = 0; i < piles.size(); i++) {
            System.out.println("Pilha " + (i + 1) + ": " + piles.get(i));
        }
    }

    public static boolean isValidPile(int pileIndex, int numPiles) {
        return pileIndex >= 0 && pileIndex < numPiles;
    }

    public static void solucaoAutomatica(List<Stack<Integer>> piles, int numNumbers) {
        Stack<Integer> source = piles.get(0);
        Stack<Integer> destination = piles.get(2);
        Stack<Integer> auxiliary = piles.get(1);

        hanoi(numNumbers, source, auxiliary, destination);
    }

    public static void hanoi(int n, Stack<Integer> source, Stack<Integer> auxiliary, Stack<Integer> destination) {
        if (n == 1) {
            move(source, destination);
            return;
        }

        hanoi(n - 1, source, destination, auxiliary);
        move(source, destination);
        hanoi(n - 1, auxiliary, source, destination);
    }

    public static void move(Stack<Integer> source, Stack<Integer> destination) {
        if (!source.isEmpty() && (destination.isEmpty() || source.peek() < destination.peek())) {
            destination.push(source.pop());
        }
    }

    public static void checkSolution(int moves, int minimumMoves) {
        if (moves < minimumMoves) {
            System.out.println("Você poderia ter feito melhor!");
        }
    }
}