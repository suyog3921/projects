import React from "react";
import { Link } from "react-router-dom";

export const Upcoming = () => {
  return (
    <div>
      <section className="upcome">
        <div className="heading flexSB">
          <h1>{Title}</h1>
          <Link to="/">View All</Link>
        </div>
        <div className="content">
          {DataTransferItemList.map((item) => (
            <Ucard key={item.id} item={item} />
          ))}
        </div>
      </section>
    </div>
  );
};
