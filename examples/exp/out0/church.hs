-- generated by hosc0 from examples/exp/church.hs

data Nat  = Z  | S Nat;
data Boolean  = False  | True ;

case  ((x (\p9-> (S p9))) Z)  of {
  Z  ->
    case  ((y (\t15-> (S t15))) Z)  of {
      Z  -> case  ((x (\t13-> (S t13))) ((y (\y22-> (S y22))) Z))  of { Z  -> True; S w11 -> False; };
      S z8 ->
        case  ((x (\r4-> (S r4))) ((y (\p10-> (S p10))) Z))  of {
          Z  -> False;
          S s16 ->
            (letrec
              f=(\z23->
                (\u23->
                  case  z23  of {
                    Z  -> case  u23  of { Z  -> True; S u11 -> False; };
                    S y11 -> case  u23  of { Z  -> False; S t5 -> ((f y11) t5); };
                  }))
            in
              ((f z8) s16));
        };
    };
  S p ->
    case  ((x (\y15-> (S y15))) ((y (\w4-> (S w4))) Z))  of {
      Z  -> False;
      S u19 ->
        (letrec
          g=(\v23->
            (\w23->
              case  v23  of {
                Z  ->
                  case  ((y (\t11-> (S t11))) Z)  of {
                    Z  -> case  w23  of { Z  -> True; S y6 -> False; };
                    S y21 ->
                      (letrec
                        h=(\p23->
                          (\r23->
                            case  p23  of {
                              Z  -> False;
                              S u ->
                                case  r23  of {
                                  Z  -> case  u  of { Z  -> True; S r19 -> False; };
                                  S p22 -> ((h u) p22);
                                };
                            }))
                      in
                        ((h w23) y21));
                  };
                S p5 -> case  w23  of { Z  -> False; S w -> ((g p5) w); };
              }))
        in
          ((g p) u19));
    };
}
