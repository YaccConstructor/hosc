-- generated by hosc0 from examples/sc/zip2.hs

data List a = Nil  | Cons a (List a);
data Pair a b = P a b;

(letrec
  f=(\p13->
    (\r13->
      case  p13  of {
        Nil  -> Nil;
        Cons z2 r3 -> case  r13  of { Nil  -> Nil; Cons u2 t11 -> (Cons (P (f1 z2) (f2 u2)) ((f r3) t11)); };
      }))
in
  ((f l1) l2))
