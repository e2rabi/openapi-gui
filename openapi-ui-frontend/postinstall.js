import { exec } from 'child_process';
import os from 'os';

if (os.platform() === 'win32') {
  console.log('Skipping rollup install');
} else {
  exec('npm install @rollup/rollup-linux-x64-gnu', (error, stdout, stderr) => {
    if (error) {
      console.error(`Error: ${error.message}`);
      return;
    }
    if (stderr) {
      console.error(`Stderr: ${stderr}`);
      return;
    }
    console.log(`Stdout: ${stdout}`);
  });
}