export default function Layout({ children }) {
  return (
    <div className="page-wrap">
      <div className="page-inner">{children}</div>
    </div>
  );
}
