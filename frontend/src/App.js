import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import './App.css'

function App() {
  return (
      <Router>
          <Routes>
            <Route path="/" element={<div><p>Home</p></div>} />
            <Route path="/login" element={<div><p>Login</p></div>} />
            <Route path="/signup" element={<div><p>Signup</p></div>} />

            <Route path="/profile" element={<div><p>Profile</p></div>} />
            <Route path="/collection/:id" element={<div><p>Collection</p></div>} />
            <Route path="/search-books" element={<div><p>Search books</p></div>} />

            {/*<Route path="*" element={<Navigate to="/" />} />*/}

          </Routes>
      </Router>

  );
}

export default App;