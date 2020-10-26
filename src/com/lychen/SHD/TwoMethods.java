package com.lychen.SHD;

import java.io.IOException;
import java.util.*;

public class TwoMethods {
    private static Shd shd = new Shd();

    private static void mhsPrint(BitSet edge) {
        int w = edge.nextSetBit(1);
        while (w != -1) {
            System.out.print(w);
            w = edge.nextSetBit(w + 1);
            if (w != -1) System.out.print(" ");
        }
        System.out.println();
    }

    public static void dfs(Hypergraph crit, BitSet uncov, Hypergraph H, Hypergraph T, BitSet S, int tail) {
        if (uncov.isEmpty()) {
            mhsPrint(S);
            return;
        }
        // 论文提的剪枝没实现
        for (int i = tail + 1; i < H.num_verts(); ++i) {
            if (!shd.vertex_would_violate(crit, uncov, H, T, S, i)) {
                HashMap<Integer, BitSet> temp = shd.update_crit_and_uncov(crit, uncov, H, T, S, i);
                BitSet ss = (BitSet) S.clone();
                ss.flip(i);
                dfs(crit, uncov, H, T, ss, i);
                shd.restore_crit_and_uncov(crit, uncov, S, temp, i);
            }
        }
    }

    private static boolean any_edge_critical_after_i(Hypergraph crit, BitSet S, int i) {
        int w = S.nextSetBit(1);
        while (w != -1) {
            int fc = crit.getEdges().get(w).nextSetBit(0);
            if (fc >= i) return true;
            w = S.nextSetBit(w + 1);
        }
        return false;
    }

    public static void rs(Hypergraph crit, BitSet uncov, Hypergraph H, Hypergraph T, BitSet S) {
        if (uncov.isEmpty()) {
            mhsPrint(S);
            return;
        }

        int i = uncov.nextSetBit(0);
        BitSet edge = H.getEdges().get(i);
        int w = edge.nextSetBit(1);
        while (w != -1) {
            // 两个冲突检测好像多余了 vertex_would_violate和any_edge_critical_after_i
            if (!shd.vertex_would_violate(crit, uncov, H, T, S, w)){
                HashMap<Integer, BitSet> hs = shd.update_crit_and_uncov(crit, uncov, H, T, S, w);
                if (!any_edge_critical_after_i(crit, S, i)) {
                    S.set(w);
                    rs(crit, uncov, H, T, S);
                    S.clear(w);
                }
                shd.restore_crit_and_uncov(crit, uncov, S, hs, w);
            }
            w = edge.nextSetBit(w + 1);
        }

    }

    public static void main(String[] args) throws IOException {
        Hypergraph H = new Hypergraph("src/example.dat");
        Hypergraph crit = new Hypergraph(H.num_edges(), H.num_verts());
        BitSet uncov = new BitSet(H.num_edges());
        BitSet S = new BitSet(H.num_verts());
        uncov.flip(0, H.num_edges());
        //dfs(crit, uncov, H, H.transpose(), S, 0);

        rs(crit, uncov, H, H.transpose(), S);
    }
}
