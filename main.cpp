//
//  main.cpp
//  othelloAlgorithm
//
//  Created by NITESH CHOUBEY on 10/11/24.
//
#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>
#include <climits>

class Othello {
public:
    int turn;
    int winner;
    std::vector<std::vector<int>> board;
    
    // Helper functions
    bool is_full() const {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 5) return false;
            }
        }
        return true;
    }
    
    int count_pieces(int piece_type) const {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == piece_type) count++;
            }
        }
        return count;
    }
    
    std::vector<std::vector<int>> copy_board() const {
        return board;
    }
    
    void replace_direction(int h, int k, int rep, int antirep, std::vector<std::vector<int>>& hu,
                          int dh, int dk) {
        int orig_h = h, orig_k = k;
        h += dh;
        k += dk;
        
        // Check validity
        int test_h = h, test_k = k;
        bool valid = false;
        while (test_h >= 0 && test_h < 8 && test_k >= 0 && test_k < 8 &&
               hu[test_h][test_k] == antirep) {
            test_h += dh;
            test_k += dk;
            if (test_h >= 0 && test_h < 8 && test_k >= 0 && test_k < 8 &&
                hu[test_h][test_k] == rep) {
                valid = true;
                break;
            }
        }
        
        // Make replacements if valid
        if (valid) {
            hu[orig_h][orig_k] = rep;
            while (h >= 0 && h < 8 && k >= 0 && k < 8 && hu[h][k] == antirep) {
                hu[h][k] = rep;
                h += dh;
                k += dk;
            }
        }
    }
    
    void rearrange_board(std::vector<std::vector<int>>& hu, int h, int k, int rep) {
        int antirep = (rep == 0) ? 1 : 0;
        
        // All 8 directions
        const std::vector<std::pair<int, int>> directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        
        for (const auto& dir : directions) {
            int new_h = h + dir.first;
            int new_k = k + dir.second;
            if (new_h >= 0 && new_h < 8 && new_k >= 0 && new_k < 8 &&
                hu[new_h][new_k] == antirep) {
                replace_direction(h, k, rep, antirep, hu, dir.first, dir.second);
            }
        }
    }
    
    std::vector<std::pair<int, int>> get_valid_moves(const std::vector<std::vector<int>>& hu, int rep) const {
        std::vector<std::pair<int, int>> moves;
        std::vector<std::vector<bool>> visited(8, std::vector<bool>(8, false));
        int antirep = (rep == 0) ? 1 : 0;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (hu[i][j] == rep) {
                    // Check all 8 directions
                    const std::vector<std::pair<int, int>> directions = {
                        {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
                    };
                    
                    for (const auto& dir : directions) {
                        int h = i + dir.first;
                        int k = j + dir.second;
                        int count = 0;
                        
                        while (h >= 0 && h < 8 && k >= 0 && k < 8 && hu[h][k] == antirep) {
                            count++;
                            h += dir.first;
                            k += dir.second;
                        }
                        
                        if (count > 0 && h >= 0 && h < 8 && k >= 0 && k < 8 &&
                            hu[h][k] == 5 && !visited[h][k]) {
                            moves.push_back({h, k});
                            visited[h][k] = true;
                        }
                    }
                }
            }
        }
        return moves;
    }
    
    int evaluate_position(const std::vector<std::vector<int>>& hu) const {
        int black_count = count_pieces(0);
        int white_count = count_pieces(1);
        return (turn == 0) ? (black_count - white_count) : (white_count - black_count);
    }
    
    int minimax(int depth, bool maximizing_player, const std::vector<std::vector<int>>& current_board,int current_turn, int alpha, int beta) {
        if (depth == 0) {
            return evaluate_position(current_board);
        }
        
        auto moves = get_valid_moves(current_board, current_turn);
        if (moves.empty()) {
            return evaluate_position(current_board);
        }
        
        if (maximizing_player) {
            int max_eval = INT_MIN;
            for (const auto& move : moves) {
                auto new_board = current_board;
                rearrange_board(new_board, move.first, move.second, current_turn);
                int eval = minimax(depth - 1, false, new_board, 1 - current_turn, alpha, beta);
                max_eval = std::max(max_eval, eval);
                alpha = std::max(alpha, eval);
                if (beta <= alpha) break;
            }
            return max_eval;
        } else {
            int min_eval = INT_MAX;
            for (const auto& move : moves) {
                auto new_board = current_board;
                rearrange_board(new_board, move.first, move.second, current_turn);
                int eval = minimax(depth - 1, true, new_board, 1 - current_turn, alpha, beta);
                min_eval = std::min(min_eval, eval);
                beta = std::min(beta, eval);
                if (beta <= alpha) break;
            }
            return min_eval;
        }
    }

public:
    Othello(const std::string& filename) {
        std::ifstream file(filename);
        if (!file.is_open()) {
            throw std::runtime_error("Could not open file");
        }
        
        file >> turn;
        board.resize(8, std::vector<int>(8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                file >> board[i][j];
            }
        }
        winner = -1;
    }
    
    int get_best_move(int depth) {
        auto moves = get_valid_moves(board, turn);
        if (moves.empty()) return -1;
        
        int best_score = INT_MIN;
        std::pair<int, int> best_move = moves[0];
        
        for (const auto& move : moves) {
            auto new_board = board;
            rearrange_board(new_board, move.first, move.second, turn);
            int score = minimax(depth - 1, false, new_board, 1 - turn, INT_MIN, INT_MAX);
            
            if (score > best_score ||
                (score == best_score && move.first * 8 + move.second <
                 best_move.first * 8 + best_move.second)) {
                best_score = score;
                best_move = move;
            }
        }
        
        return best_move.first * 8 + best_move.second;
    }
    
    std::vector<int> play_full_game(int depth) {
        std::vector<int> moves;
        while (!is_full()) {
            int move = get_best_move(depth);
            if (move == -1) {
                turn = 1 - turn;
                continue;
            }
            
            moves.push_back(move);
            int h = move / 8;
            int k = move % 8;
            rearrange_board(board, h, k, turn);
            turn = 1 - turn;
        }
        
        int black_count = count_pieces(0);
        int white_count = count_pieces(1);
        winner = (black_count > white_count) ? 0 : (black_count < white_count) ? 1 : -1;
        
        return moves;
    }
    
    void print_board() const {
        std::cout << "     0    1    2    3    4    5    6    7\n";
        for (int i = 0; i < 8; i++) {
            std::cout << i << "    ";
            for (int j = 0; j < 8; j++) {
                std::cout << board[i][j] << "    ";
            }
            std::cout << "\n";
        }
    }
    
    int get_winner() const { return winner; }
    int get_turn() const { return turn; }
};

int main() {
    std::cout << "Welcome to Othello game\n";
    std::cout << "Note: Empty blocks are represented by 5\n";
    std::cout << "Select difficulty level (1-5):\n";
    std::cout << "1) Extremely easy\n2) Easy\n3) Medium\n4) Hard\n5) Extremely hard\n";
    
    int difficulty;
    std::cin >> difficulty;
    
    std::cout << "You are player 0\n";
    
    try {
        Othello game("filename.txt");
        std::cout << "Initial board:\n";
        game.print_board();
        
        // Map difficulty to search depth
        const std::vector<int> depth_map = {1, 2, 4, 7, 10};
        int search_depth = depth_map[difficulty - 1];
        
        while (!game.is_full()) {
            // Player's turn
            auto moves = game.get_valid_moves(game.board, 0);
            if (moves.empty()) {
                std::cout << "No valid moves available for you!\n";
            } else {
                int x, y;
                std::cout << "Enter your move (row column): ";
                std::cin >> x >> y;
                
                // Validate move
                bool valid_move = false;
                for (const auto& move : moves) {
                    if (move.first == x && move.second == y) {
                        valid_move = true;
                        break;
                    }
                }
                
                if (!valid_move) {
                    std::cout << "Invalid move! Game over.\n";
                    return 0;
                }
                
                game.rearrange_board(game.board, x, y, 0);
                std::cout << "Board after your move:\n";
                game.print_board();
            }
            
            // Computer's turn
            int computer_move = game.get_best_move(search_depth);
            if (computer_move != -1) {
                int x = computer_move / 8;
                int y = computer_move % 8;
                std::cout << "Computer plays: " << x << " " << y << "\n";
                game.rearrange_board(game.board, x, y, 1);
                std::cout << "Board after computer's move:\n";
                game.print_board();
            } else {
                std::cout << "No valid moves available for computer!\n";
            }
        }
        
        // Game over
        int black_count = game.count_pieces(0);
        int white_count = game.count_pieces(1);
        if (white_count > black_count) {
            std::cout << "Computer wins " << white_count << ":" << black_count << "\n";
        } else if (black_count > white_count) {
            std::cout << "You win " << black_count << ":" << white_count << "\n";
        } else {
            std::cout << "It's a draw!\n";
        }
        
    } catch (const std::exception& e) {
        std::cout << "Error: " << e.what() << "\n";
        return 1;
    }
    
    return 0;
}
