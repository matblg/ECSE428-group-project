import React, { useState } from 'react';
import './BookSearch.css';

function BookSearch() {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [isbn, setIsbn] = useState('');
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [searched, setSearched] = useState(false);

  const handleSearch = async (e) => {
    e.preventDefault();

    // Check if at least one field has input
    if (!title.trim() && !author.trim() && !isbn.trim()) {
      setError('Please enter at least one search criterion (title, author, or ISBN)');
      return;
    }

    setLoading(true);
    setError(null);
    setSearched(true);

    try {
      // Build query parameters
      const params = new URLSearchParams();
      if (title.trim()) params.append('title', title.trim());
      if (author.trim()) params.append('author', author.trim());
      if (isbn.trim()) params.append('isbn', isbn.trim());

      const response = await fetch(`/api/books/search?${params.toString()}`);

      if (!response.ok) {
        throw new Error('Failed to fetch books');
      }

      const data = await response.json();
      setBooks(data.items || []);
    } catch (err) {
      setError(err.message || 'An error occurred while searching');
      setBooks([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="book-search-container">
      <h1>Search Books</h1>

      <form onSubmit={handleSearch} className="search-form">
        <div className="search-fields">
          <div className="search-field">
            <label htmlFor="title">Title</label>
            <input
              type="text"
              id="title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="e.g., Harry Potter"
              className="search-input"
            />
          </div>

          <div className="search-field">
            <label htmlFor="author">Author</label>
            <input
              type="text"
              id="author"
              value={author}
              onChange={(e) => setAuthor(e.target.value)}
              placeholder="e.g., J.K. Rowling"
              className="search-input"
            />
          </div>

          <div className="search-field">
            <label htmlFor="isbn">ISBN</label>
            <input
              type="text"
              id="isbn"
              value={isbn}
              onChange={(e) => setIsbn(e.target.value)}
              placeholder="e.g., 9780545010221"
              className="search-input"
            />
          </div>
        </div>

        <button type="submit" className="search-button" disabled={loading}>
          {loading ? 'Searching...' : 'Search'}
        </button>
      </form>

      {error && <div className="error-message">{error}</div>}

      {loading && <div className="loading">Searching for books...</div>}

      {!loading && searched && books.length === 0 && !error && (
        <div className="no-results">No books found. Try a different search query.</div>
      )}

      {!loading && books.length > 0 && (
        <div className="books-grid">
          {books.map((book) => (
            <div key={book.id} className="book-card">
              <div className="book-image">
                {book.volumeInfo.imageLinks?.thumbnail ? (
                  <img
                    src={book.volumeInfo.imageLinks.thumbnail}
                    alt={book.volumeInfo.title}
                  />
                ) : (
                  <div className="no-image">No Image</div>
                )}
              </div>

              <div className="book-details">
                <h3 className="book-title">{book.volumeInfo.title}</h3>

                <p className="book-authors">
                  by {book.volumeInfo.authors?.join(', ') || 'Unknown Author'}
                </p>

                {book.volumeInfo.publishedDate && (
                  <p className="book-date">Published: {book.volumeInfo.publishedDate}</p>
                )}

                {book.volumeInfo.averageRating && (
                  <p className="book-rating">
                    Rating: {book.volumeInfo.averageRating} / 5
                    {book.volumeInfo.ratingsCount && ` (${book.volumeInfo.ratingsCount} ratings)`}
                  </p>
                )}

                {book.volumeInfo.pageCount && (
                  <p className="book-pages">{book.volumeInfo.pageCount} pages</p>
                )}

                {book.volumeInfo.categories && book.volumeInfo.categories.length > 0 && (
                  <p className="book-categories">
                    Categories: {book.volumeInfo.categories.join(', ')}
                  </p>
                )}

                <p className="book-description">
                  {book.volumeInfo.description
                    ? (book.volumeInfo.description.length > 200
                        ? book.volumeInfo.description.substring(0, 200) + '...'
                        : book.volumeInfo.description)
                    : 'No description available.'}
                </p>

                <div className="book-links">
                  {book.volumeInfo.previewLink && (
                    <a
                      href={book.volumeInfo.previewLink}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="book-link"
                    >
                      Preview
                    </a>
                  )}
                  {book.volumeInfo.infoLink && (
                    <a
                      href={book.volumeInfo.infoLink}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="book-link"
                    >
                      More Info
                    </a>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default BookSearch;
