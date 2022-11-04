clear;

in = double(csvread("12003.csv"));
out = fft2(in); 
amp=abs(out);
phase = angle(out);
csvwrite("12003_Amplitudes_DFT.csv",amp/10^2);
csvwrite("12003_Phases_DFT.csv",phase);

