import java.io.*;
import java.util.*;


public class Othello {
    int turn;
    int winner;
    int board[][];
    

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j){
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
    }



    public int boardScore() {
        int now=0;
        int nob=0;
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j){
               if (board[i][j]==1){
                    now++;
                }
                else if (board[i][j]==0) {
                     nob++;
                }
            }
        }
        if (turn==0){
            return nob-now;
        }
        else if (turn==1) {
            return now-nob;
        }
        return 0;
    }

    private void going_southeast(ArrayList<int[]> res,int[][] visited,int ct,int h,int y,int t,int[][] hu){
        int yc = h;
        h++;
        y++;
        while (hu[h][y] == ct) {
            h++;
            y++;
            if (h<=7 && y<=7){
                continue;
            }
            else{
                break;
            }
        }
        if (yc + 1 < h) {
            if (h <= 7 && y<=7 && hu[h][y]!=t) {
                int[] entry;
                entry = new int[2];
                entry[0] = h;
                entry[1] = y;
                if (visited[h][y]==0){
                    res.add(entry);
                    visited[h][y]=1;
                }
            }
        }
    }
    
    private void go_southwest(ArrayList<int[]> res,int[][] visited,int ct,int h,int y,int t,int[][] hu){
        int yc = h;
        h++;
        y--;
        while (hu[h][y] == ct) {
            h++;
            y--;
            if (h<=7 && y>=0){
                continue;
            }
            else{
                break;
            }
        }
        if (yc + 1 < h) {
            if (h <= 7 && y>=0 && hu[h][y]!=t) {
                int[] entry;
                entry = new int[2];
                entry[0] = h;
                entry[1] = y;
                if (visited[h][y]==0){
                    res.add(entry);
                    visited[h][y]=1;
                }
            }
        }
        //            h = yc;
        //            y=hc;
    }

    private void go_northeast(ArrayList<int[]> res,int[][] visited,int ct,int h,int y,int t,int[][] hu){
        int yc = h;
        h--;
        y++;
        while (hu[h][y] == ct) {
            h--;
            y++;
            if (h>=0 && y<=7){
                continue;
            }
            else{
                break;
            }
        }
        if (yc - 1 > h) {
            if (h >= 0 && y<=7 && hu[h][y]!=t) {
                int[] entry;
                entry = new int[2];
                entry[0] = h;
                entry[1] = y;
                if (visited[h][y]==0){
                    res.add(entry);
                    visited[h][y]=1;
                }
            }
        }
//            h = yc;
//            y=hc;
    }

    private void go_northwest(ArrayList<int[]> res,int[][] visited,int ct,int h,int y,int t,int[][] hu){
        int yc = h;
        // int hc=y;
        h--;
        y--;
        while (hu[h][y] == ct) {
            h--;
            y--;
            if (h>=0 && y>=0){
                continue;
            }
            else{
                break;
            }
        }
        if (yc - 1 > h) {
            if (h >= 0 && y>=0 && hu[h][y]!=t) {
                int[] entry;
                entry = new int[2];
                entry[0] = h;
                entry[1] = y;
                if (visited[h][y]==0){
                    res.add(entry);
                    visited[h][y]=1;
                }
            }
        }
//            h = yc;
//            y=hc;
    }

    private void helper_positions_available(ArrayList<int[]> res,int t,int[][] hu){
        int[][] visited;
        visited = new int[8][8];
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                visited[i][j]=0;
            }
        }
        int ct;
        if (t==0){
            ct=1;
        }
        else{
            ct=0;
        }
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (hu[i][j]==t){
                    int h=i;
                    int y=j;
                    if (y<7) {
                        int yc=y;
                        y++;
                        while (hu[h][y] == ct){
                            y++;
                            if (y<=7){
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                        if (yc+1<y){
                            if (y<=7 && hu[h][y]!=t){
                                int[] entry;
                                entry=new int[2];
                                entry[0]=h;
                                entry[1]=y;
                                if (visited[h][y]==0){
                                    res.add(entry);
                                    visited[h][y]=1;
                                }
                            }
                        }
                        y=yc;
                    }
                    if (y>0) {
                        int yc = y;
                        y--;
                        while (hu[h][y] == ct) {
                            y--;
                            if (y>=0){
                                continue;
                            }
                            else {
                                break;
                            }
                        }
                        if (yc - 1 > y) {
                            if (y >= 0 && hu[h][y]!=t) {
                                int[] entry;
                                entry = new int[2];
                                entry[0] = h;
                                entry[1] = y;
                                if (visited[h][y]==0){
                                    res.add(entry);
                                    visited[h][y]=1;
                                }
                            }
                        }
                        y = yc;
                    }
                    if (h>0) {
                        int yc = h;
                        h--;
                        while (hu[h][y] == ct) {
                            h--;
                            if (h>=0){
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                        if (yc - 1 > h) {
                            if (h >= 0 && hu[h][y]!=t) {
                                int[] entry;
                                entry = new int[2];
                                entry[0] = h;
                                entry[1] = y;
                                if (visited[h][y]==0){
                                    res.add(entry);
                                    visited[h][y]=1;
                                }
                            }
                        }
                        h = yc;
                    }
                    if (h<7) {
                        int yc = h;
                        h++;
                        while (hu[h][y] == ct) {
                            h++;
                            if (h<=7){
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                        if (yc + 1 < h) {
                            if (h <= 7 && hu[h][y]!=t) {
                                int[] entry;
                                entry = new int[2];
                                entry[0] = h;
                                entry[1] = y;
                                if (visited[h][y]==0){
                                    res.add(entry);
                                    visited[h][y]=1;
                                }
                            }
                        }
                        h = yc;
                    }
                    if (h==0 && y==0){
                        going_southeast(res,visited,ct,0,0,t,hu);
                    }

                    if (h==0 && y==7){
                        go_southwest(res,visited,ct,0,7,t,hu);
                    }
                    if (h==7 && y==0){
                        go_northeast(res,visited,ct,7,0,t,hu);
                    }
                    if (h==7 && y==7){
                        go_northwest(res,visited,ct,7,7,t,hu);
                    }

                    if (h!=0 && h!=7 && y==7) {
                        go_northwest(res,visited,ct,h,7,t,hu);
                        go_southwest(res,visited,ct,h,7,t,hu);

                    }

                    if (h!=0 && h!=7 && y==0){
                        go_northeast(res,visited,ct,h,0,t,hu);
                        going_southeast(res,visited,ct,h,0,t,hu);

                    }

                    if (y!=0 && y!=7 && h==0){
                        go_southwest(res,visited,ct,h,y,t,hu);
                        going_southeast(res,visited,ct,h,y,t,hu);
                    }

                    if (y!=0 && y!=7 && h==7){
                        go_northwest(res,visited,ct,h,y,t,hu);
                        go_northeast(res,visited,ct,h,y,t,hu);
                    }
                    if (h!=0 && h!=7 && y!=0 && y!=7){
                        go_northeast(res,visited,ct,h,y,t,hu);
                        go_northwest(res,visited,ct,h,y,t,hu);
                        go_southwest(res,visited,ct,h,y,t,hu);
                        going_southeast(res,visited,ct,h,y,t,hu);
                    }
                }
            }
        }
    }

    private int count_black(int[][] hu){
        int count=0;
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (hu[i][j]==0){
                    count++;
                }
            }
        }
        return count;
    }

    private int count_white(int[][] hu){
        int count=0;
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (hu[i][j]==1){
                    count++;
                }
            }
        }
        return count;
    }

    private void replace_east(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        k++;
        int thik=k;
        int yoi=0;
        while(hu[h][thik]==antirep){
            // hu[h][thik]=rep;
            thik++;
            if (thik>7){
                break;
            }
            if(hu[h][thik]==rep){
                yoi=1;
                break;
            }
        }
        if(yoi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                k++;
                if (k>7){
                    break;
                }
            }
        }   
    }

    private void replace_west(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        k--;
        int thik=k;
        int yoi=0;
        while(hu[h][thik]==antirep){
            // hu[h][thik]=rep;
            thik--;
            if (thik<0){
                break;
            }
            if(hu[h][thik]==rep){
                yoi=1;
                break;
            }
        }
        if(yoi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                k--;
                if (k<0){
                    break;
                }
            }
        }
    }

    private void replace_north(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        h--;
        int thik=h;
        int yoi=0;
        while(hu[thik][k]==antirep){
            // hu[h][thik]=rep;
            thik--;
            if (thik<0){
                break;
            }
            if(hu[thik][k]==rep){
                yoi=1;
                break;
            }
        }
        if(yoi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                h--;
                if (h<0){
                    break;
                }
            }
        } 
    }

    private void replace_south(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        h++;
        int thik=h;
        int yoi=0;
        while(hu[thik][k]==antirep){
            // hu[h][thik]=rep;
            thik++;
            if (thik>7){
                break;
            }
            if(hu[thik][k]==rep){
                yoi=1;
                break;
            }
        }
        if(yoi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                h++;
                if (h>7){
                    break;
                }
            }
        }
        
    }

    private void replace_north_east(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        h--;
        k++;
        int lpoi=h;
        int lyoi=k;
        int juyi=0;
        while(hu[lpoi][lyoi]==antirep){
            lpoi--;
            lyoi++;
            if (lpoi<0 || lyoi>7){
                break;
            }
            if(hu[lpoi][lyoi]==rep){
                juyi=1;
                break;
            }
        }
        if(juyi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                h--;
                k++;
                if (h<0 || k>7){
                    break;
                }
            }
        }
    }

    private void replace_north_west(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        int lpoi=h;
        int lyoi=k;
        lpoi--;
        lyoi--;
        int juyi=0;
        while(hu[lpoi][lyoi]==antirep){
            lpoi--;
            lyoi--;
            if (lpoi<0 || lyoi<0){
                break;
            }
            if(hu[lpoi][lyoi]==rep){
                juyi=1;
                break;
            }
        }
        h--;
        k--;
        if(juyi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                h--;
                k--;
                // if()
                if (h<0 || k<0){
                    break;
                }
            }
        }
    }

    private void replace_south_east(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        h++;
        k++;
        int lpoi=h;
        int lyoi=k;
        int juyi=0;
        while(hu[lpoi][lyoi]==antirep){
            lpoi++;
            lyoi++;
            if (lpoi>7 || lyoi>7){
                break;
            }
            if(hu[lpoi][lyoi]==rep){
                juyi=1;
                break;
            }
        }
        if(juyi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                h++;
                k++;
                if (h>7 || k>7){
                    break;
                }
            }
        }   
    }

    private void replace_south_west(int h,int k,int rep,int antirep,int[][] hu){
        int ytytyt=h;
        int etete=k;
        h++;
        k--;
        int lpoi=h;
        int lyoi=k;
        int juyi=0;
        while(hu[lpoi][lyoi]==antirep){
            lpoi++;
            lyoi--;
            if (lpoi>7 || lyoi<0){
                break;
            }
            if(hu[lpoi][lyoi]==rep){
                juyi=1;
                break;
            }
        }
        if(juyi==1){
            hu[ytytyt][etete]=rep;
            while (hu[h][k]==antirep){
                hu[h][k]=rep;
                h++;
                k--;
                if (h>7 || k<0){
                    break;
                }
            }
        }
    }

    private int[][] rearrange_board(int[][] hu,int h,int k,int rep){
        int antirep;
        if (rep==0){
            antirep=1;
        }
        else{
            antirep=0;
        }
        if (h+1<=7){
            if (hu[h+1][k]==antirep){
                replace_south(h, k, rep, antirep, hu);
            }
        }
        if (h-1>=0){
            if (hu[h-1][k]==antirep){
                replace_north(h, k, rep, antirep, hu);
            }
        }
        if (k+1<=7){
            if (hu[h][k+1]==antirep ){ 
                replace_east(h,k,rep,antirep,hu);
            }
        }
        if (k-1>=0){
            if (hu[h][k-1]==antirep){
                replace_west(h,k,rep,antirep,hu);
            }
        }
        
        if (k-1>=0 && h-1>=0){
            if (hu[h-1][k-1]==antirep){
                replace_north_west(h,k,rep,antirep,hu);
            }
        }
        if (k-1>=0 && h+1<=7){
            if (hu[h+1][k-1]==antirep){
                replace_south_west(h,k,rep,antirep,hu);
            }
        }
        if (k+1<=7 && h+1<=7){
            if (hu[h+1][k+1]==antirep){
                replace_south_east(h,k,rep,antirep,hu);
            }
        }
        if (k+1<=7 && h-1>=0){
            if (hu[h-1][k+1]==antirep){
                replace_north_east(h,k,rep,antirep,hu);
            }
        }
        return hu;
    }

    private ArrayList<int[]> positions_available(int[][] hu,int rep){
        ArrayList<int[]> res=new ArrayList<>();
        helper_positions_available(res,rep,hu);
        return res;
    }

    private int score_after_rearranging(int h,int k,int rep,int[][] hu){

        int[][] jh;
        jh=rearrange_board(hu,h,k,rep);
        if(turn==0){
            return count_black(jh)-count_white(jh);
        }
        else{
            return count_white(jh)-count_black(jh);
        }
        
    }

    private int[][] copyy(int[][] jiop){
        int copy[][] = new int[8][8];
        for(int i = 0; i < 8; ++i){
            for (int j=0;j<8;j++){
                copy[i][j]=jiop[i][j];
            }
        }  
        return copy;
    }

    private int mini_max(int depth,boolean my_turn,ArrayList<int[]> pos_ava,int rep,int[][] cp_board,int how_deep,ArrayList<Integer> fi_answer){
        int antirep;
        if (rep==0){
            antirep=1;
        }
        else{
            antirep=0;
        }
        if(rep==0 && depth==1){
            if (how_deep%2==0){
                int min_valieoe=500;
                for (int i=0;i<pos_ava.size();i++){
                    int[] ints=pos_ava.get(i);
                    int h = ints[0];
                    int k = ints[1];
                    int[][] cop_board =copyy(cp_board);
                    int val=score_after_rearranging(pos_ava.get(i)[0],pos_ava.get(i)[1],rep,cop_board);
                    if (val<=min_valieoe){
                        int u = min_valieoe;
                        min_valieoe=val;
                        if(depth==how_deep){
                            if(u>min_valieoe && fi_answer.size()>0){
                                fi_answer.removeAll(fi_answer);
                            }
                            fi_answer.add(8*h+k);
                        }
                    }
                }
                return min_valieoe;
            }
            else{
                int max_validid=-500;
                for (int i=0;i<pos_ava.size();i++){
                    int[] ints=pos_ava.get(i);
                    int[][] cop_board =copyy(cp_board);
                    
                    int h = ints[0];
                    int k = ints[1];
                    int val=score_after_rearranging(pos_ava.get(i)[0],pos_ava.get(i)[1],rep,cop_board);
                    if (val>=max_validid){
                        int u = max_validid;
                        max_validid=val;
                        if(depth==how_deep){
                            if(u<max_validid && fi_answer.size()>0){
                                fi_answer.removeAll(fi_answer);
                            }
                            fi_answer.add(8*h+k);
                        }
                    }
                }
            return max_validid;
            }
        }
        else if (rep==1 && depth==1){
            if (how_deep%2==0){
                int min_valieoe=500;
                for (int i=0;i<pos_ava.size();i++){
                    int[] ints=pos_ava.get(i);
                    int h = ints[0];
                    int k = ints[1];
                    int[][] cop_board =copyy(cp_board);
                    int val=score_after_rearranging(pos_ava.get(i)[0],pos_ava.get(i)[1],rep,cop_board);
                    if (val<=min_valieoe){
                        int u =min_valieoe;
                        min_valieoe=val;
                        if(depth==how_deep){
                            if(u>min_valieoe && fi_answer.size()>0){
                                fi_answer.removeAll(fi_answer);
                            }
                            fi_answer.add(8*h+k);
                        }
                    }
                }
                return min_valieoe;
            }
            else{
                int max_validid=-500;
                for (int i=0;i<pos_ava.size();i++){
                    int[] ints=pos_ava.get(i);
                    int[][] cop_board =copyy(cp_board);
                    
                    int h = ints[0];
                    int k = ints[1];
                    int val=score_after_rearranging(pos_ava.get(i)[0],pos_ava.get(i)[1],rep,cop_board);
                    if (val>=max_validid){
                        int u = max_validid;
                        max_validid=val;
                        if(depth==how_deep){
                            if(u<max_validid && fi_answer.size()>0){
                                fi_answer.removeAll(fi_answer);
                            }
                            fi_answer.add(8*h+k);
                        }
                    }
                }
            return max_validid;
            }
        }
        if (my_turn){
            int max_value=-500;
                for (int i=0;i<pos_ava.size();i++) {
                    int[] ints=pos_ava.get(i);
                    int[][] cop_board = copyy(cp_board);
                    int[][] cop_board_two =copyy(cp_board);
                    int h = ints[0];
                    int k = ints[1];
                    cop_board = rearrange_board(cop_board, h, k, rep);
                    ArrayList<int[]> PA=positions_available(cop_board,antirep);
                    int val;
                    if(PA.size()==0){
                        val=mini_max(depth-1, false, pos_ava, antirep, cop_board_two, how_deep, fi_answer);

                    }
                    else{
                        val = mini_max(depth - 1, false, PA, antirep, cop_board,how_deep,fi_answer);
                    }
                    if (val>=max_value){
                        int u = max_value;
                        max_value=val;
                        if(depth==how_deep){
                            if(max_value>u && fi_answer.size()>0){
                                fi_answer.removeAll(fi_answer);
                            }
                            fi_answer.add(8*h+k);
                        }
                    }
                }
            return max_value;
        }
        else{
            int min_value=500;
            for (int i=0;i<pos_ava.size();i++) {
                int[] ints=pos_ava.get(i);
                int[][] cop_board = copyy(cp_board);
                int[][] cop_board_two = copyy(cp_board);
                int h = ints[0];
                int k = ints[1];
                cop_board = rearrange_board(cop_board, h, k, rep);
                ArrayList<int[]> PA = positions_available(cop_board,antirep);
                int val;
                if(PA.size()==0){
                    val=mini_max(depth-1, false, pos_ava, antirep, cop_board_two, how_deep, fi_answer);
                }
                else{
                    val = mini_max(depth - 1, false, PA, antirep, cop_board,how_deep,fi_answer);
                }                    
                if (val<=min_value){
                    int u = min_value;
                    min_value=val;
                    if(depth==how_deep){
                        if(u>min_value && fi_answer.size()>0){
                            fi_answer.removeAll(fi_answer);
                        }
                        fi_answer.add(8*h+k);
                    }
                }
            }
            return min_value;
        }
    }



    public int bestMove(int k) {
        /* Complete this function to build a Minimax tree of depth k (current board being at depth 0),
         * for the current player (siginified by the variable turn), and propagate scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j; return i*8+j
         * In case of ties, return the smallest integer value representing the tile with best score.
         * 
         * Note: Do not alter the turn variable in this function, so that the boardScore() is the score
         * for the same player throughout the Minimax tree.
        */
        boolean my_turn=true;
        ArrayList<int[]> p_available=positions_available(board,turn);
        int[][] puma=copyy(board);
        ArrayList<Integer> fi_answer=new ArrayList<>();
        int vieiei;
  
        vieiei=mini_max(k,my_turn,p_available,turn,puma,k,fi_answer);
        
        if(fi_answer.size()==0){
            return -1;
        }
        int min=fi_answer.get(0);
        for(int py=0;py<fi_answer.size();py++){
            if(fi_answer.get(py)<min){
                min=fi_answer.get(py);
            }
        }  
        return min;
    }

    private boolean is_full(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if (board[i][j]==5){
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Integer> fullGame(int k) {
        
        /* Complete this function to compute and execute the best move for each player starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and the turn
         * at each step. In the end, modify the winner variable as required.
         */
        ArrayList<Integer> what_to_do=new ArrayList<>();
        while(!is_full()){
            int bm=bestMove(k);
            if(bm==-1){
                if(turn==0){
                    turn=1;
                }
                else{
                    turn=0;
                }
                continue;
            }
            int ultimate_h;
            int ultimate_k;
            ultimate_k=bm%8;
            ultimate_h=(bm-ultimate_k)/8;
            what_to_do.add( bm);
            board=rearrange_board(board, ultimate_h, ultimate_k, turn);
            if(turn==0){
                turn=1;
            }
            else{
                turn=0;
            }
        }
        if(count_black(board)>count_white(board)){
            winner=0;
        }
        else if(count_black(board)<count_white(board)){
            winner=1;
        }
        else{
            winner=-1;
        }
        return what_to_do;
    }

    public void print_board(){
        for(int i=0;i<8;i++){
            if(i==0){
                System.out.println("     0    1    2    3    4    5    6    7");
            }
            for(int j=0;j<8;j++){
                if(j==0){
                    System.out.print(i+"    ");
                }
                System.out.print(board[i][j]+"    ");
            }
            System.out.println("");
        }
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for(int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to othello game ");
        System.out.println("Note : the topmost row and the leftmost coloumn are to help you identify the position you want to enter they are not part of the game table also empty blocks are represented by 5");
        System.out.println("select your level of difficulty : ");
        System.out.println("1) extremely easy ");
        System.out.println("2) easy ");
        System.out.println("3) medium ");
        System.out.println("4) hard");
        System.out.println("5) extremely hard");
        Scanner ert=new Scanner(System.in);
        int choice=ert.nextInt();
        ert.nextLine();
        System.out.println("you are 0 ");
        try {
            Othello u=new Othello("filename.txt");
            System.out.println("intial board");
            u.print_board();
            int choki=0;
            while(!u.is_full()){
                int master_key=0;
                int master3=0;
                u.turn=0;
                ArrayList<int[]> p_available=u.positions_available(u.board,u.turn);
                if(p_available.size()==0){
                    System.out.println("No possible move for you !!!!");
                    master_key++;
                }
                else{
                    System.out.println("select the position you want to set zero to :");
                    int x_c=ert.nextInt();
                    int y_c=ert.nextInt();
                    ert.nextLine();
                    for(int tu=0;tu<p_available.size();tu++){
                        if(p_available.get(tu)[0]==x_c  &&  p_available.get(tu)[1]==y_c){
                            master3=1;
                            break;
                        }
                    }
                    if(master3==0){
                        System.out.println("invalid move, disqualified");
                        choki=1;
                        break;
                    } 
                    u.board=u.rearrange_board(u.board, x_c, y_c, 0);
                    System.out.println("updated board after your turn is : ");
                    u.print_board();
                }
                if(choice==1){
                    u.turn=1;
                    int a=u.bestMove(1);
                    if(a!=-1){
                        int y_coordi=a%8;
                        int x_coordi=a/8;
                        System.out.println("computers selected position is X : "+x_coordi+" Y : "+y_coordi);
                        u.board=u.rearrange_board(u.board, x_coordi, y_coordi, 1);
                        System.out.println("updated board after computer's turn is : ");
                        u.print_board();
                    }
                    else{
                        System.out.println("No possible move for the computer ");
                        master_key++;
                    }                                  
                }
                if(choice==2){
                    u.turn=1;
                    int a=u.bestMove(2);
                    if(a!=-1){
                        int y_coordi=a%8;
                        int x_coordi=a/8;
                        System.out.println("computers selected position is X : "+x_coordi+" Y : "+y_coordi);
                        u.board=u.rearrange_board(u.board, x_coordi, y_coordi, 1);
                        System.out.println("updated board after computer's turn is : ");
                        u.print_board();
                    }
                    else{
                        System.out.println("No possible move for the computer ");
                        master_key++;
                    }                                  
                }
                if(choice==3){
                    u.turn=1;
                    int a=u.bestMove(4);
                    if(a!=-1){
                        int y_coordi=a%8;
                        int x_coordi=a/8;
                        System.out.println("computers selected position is X : "+x_coordi+" Y : "+y_coordi);
                        u.board=u.rearrange_board(u.board, x_coordi, y_coordi, 1);
                        System.out.println("updated board after computer's turn is : ");
                        u.print_board();
                    }
                    else{
                        System.out.println("No possible move for the computer ");
                        master_key++;
                    }                                  
                }
                if(choice==4){
                    u.turn=1;
                    int a=u.bestMove(7);
                    if(a!=-1){
                        int y_coordi=a%8;
                        int x_coordi=a/8;
                        System.out.println("computers selected position is X : "+x_coordi+" Y : "+y_coordi);
                        u.board=u.rearrange_board(u.board, x_coordi, y_coordi, 1);
                        System.out.println("updated board after computer's turn is : ");
                        u.print_board();
                    }
                    else{
                        System.out.println("No possible move for the computer ");
                        master_key++;
                    }                                  
                }
                if(choice==5){
                    u.turn=1;
                    int a=u.bestMove(10);
                    if(a!=-1){
                        int y_coordi=a%8;
                        int x_coordi=a/8;
                        System.out.println("computers selected position is X : "+x_coordi+" Y : "+y_coordi);
                        u.board=u.rearrange_board(u.board, x_coordi, y_coordi, 1);
                        System.out.println("updated board after computer's turn is : ");
                        u.print_board();
                    }
                    else{
                        System.out.println("No possible move for the computer ");
                        master_key++;
                    }                                  
                }
                if(master_key==2){
                    System.out.println("No more possible moves for both the parties so the game ends here");
                    break;
                }
            }
            if(choki==1){
                System.out.println("you are disqualified for entering an illegal move");
            }
            else{
                int no_of_zeroes=u.count_black(u.board);
                int no_of_ones=u.count_white(u.board);
                if(no_of_ones>no_of_zeroes){
                    System.out.println("computer wins by "+ no_of_ones+" : "+no_of_zeroes);
                } 
                else if(no_of_ones<no_of_zeroes){
                    System.out.println("you won!!!! by "+ no_of_ones+" : "+no_of_zeroes);
                } 
                else{
                    System.out.println("its a draw!");
                } 
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}