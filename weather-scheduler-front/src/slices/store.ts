import { configureStore } from '@reduxjs/toolkit'

import { jobReducer } from './job';
import { themeReducer } from './theme';

export const store = configureStore({
	reducer: {
		job: jobReducer,
		theme: themeReducer,
	},
	middleware: (getDefaultMiddleware) =>
		getDefaultMiddleware({
			serializableCheck: {
				// Ignore these field paths in all actions
				ignoredActionPaths: ['meta.arg', 'payload.timestamp'],
				// Ignore these paths in the state
				ignoredPaths: ['items.dates'],
			},
		}),
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
