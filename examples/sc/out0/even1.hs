-- generated by hosc0 from examples/sc/even1.hs

data Number  = Z  | S Number;
data Boolean  = True  | False ;

(letrec
  g=(\u3->
    (\v3->
      case  u3  of {
        S p1 -> ((g p1) (S (S v3)));
        Z  ->
          (letrec h=(\w3-> case  w3  of { S y2 -> case  y2  of { S v2 -> (h v2); Z  -> False; }; Z  -> True; })
          in
            (h v3));
      }))
in
  ((g n) Z))
