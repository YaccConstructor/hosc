-- generated by hosc1 from examples/eq/churchMult1.hs

data Nat  = Z  | S Nat;

(letrec
  f=(\u7->
    (\v7->
      case  u7  of {
        Z  -> Z;
        S z6 -> (letrec g=(\w7-> case  w7  of { Z  -> ((f z6) v7); S s -> (S (g s)); }) in (g v7));
      }))
in
  ((f x) y))
