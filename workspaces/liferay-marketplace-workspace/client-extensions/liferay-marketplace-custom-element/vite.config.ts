import {defineConfig} from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/

export default defineConfig({
	build: {
		outDir: 'build/static/',
		rollupOptions: {
			output: {
				assetFileNames:
					'marketplace-custom-element-assets/[name][extname]',
				chunkFileNames: '[name]-[hash].js',
				entryFileNames: '[name]-[hash].js',
			},
		},
	},
	plugins: [react()],
});
