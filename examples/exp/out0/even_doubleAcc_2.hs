-- generated by hosc0 from examples/exp/even_doubleAcc_2.hs

data Bool  = True  | False ;
data Nat  = Z  | S Nat;

(letrec
  f=(\p2->
    (\r2->
      case  p2  of {
        S u -> ((f u) (S (S r2)));
        Z  ->
          (letrec g=(\s2-> case  s2  of { Z  -> True; S y -> case  y  of { Z  -> False; S v1 -> (g v1); }; })
          in
            (g r2));
      }))
in
  ((f n1) Z))
