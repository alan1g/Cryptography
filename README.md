# Cryptography
This programme cracks a cipher code by using the Baby Steps,Giant Steps Algorithm, a memory/time trade off algorithm.

Problem:

Alice’s public key is (83187685431865799, 231541186, 58121575766172118).
Bob sends her the cipher (15714167638989179,1416052582726447).
This programme calculates Alice's private key (x) and extracts the message Bob is sending.
It uses the Baby Steps Giant Steps algorithm to reduce the complexity to O(sqrt(n)).


          p= 83187685431865799
          y= 58121575766172118 
          g= 231541186

          therefore 58121575766172118 = 231541186^x mod 83187685431865799
          in the form y = g^x mod p.
          
          m = √(p-1) = √n
          x = mi + j where,
          0 ≤ i < m  and 0 ≤ j < m.
          therefore y = g^m.i +j 
          = g^j = y(g^-m)^i.
          
Baby Steps:
          Values for g^j for all j < m are computed and quantities are stored.
          
Giant Steps:
          y(g^-m)^i are computed and compared to g^j for all j < m.

When a match is found the i and j are used to compute x = m.i + j,
This is the discrete log (private key).


