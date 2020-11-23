import React, { useState } from "react";
import "./App.css";
import { DomainStatusForm } from "./DomainStatusForm";
import { api } from "../api/client";
import { DomainStatusResponse } from "../api/domain";
import { Error } from "./Error";
import { DomainDetails } from "./DomainDetails";
import { DomainStatusDetails } from "./DomainStatusDetails";

export function App() {
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState<DomainStatusResponse | undefined>(undefined);
  const [error, setError] = useState<string | undefined>(undefined);

  const onSearch = async (domainName: string) => {
    setLoading(true);
    setError(undefined);
    setResponse(undefined);

    try {
      const response = await api.getDomainStatus(domainName);
      setResponse(response);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app bp3-dark">
      <DomainStatusForm onSearch={onSearch} loading={loading} />
      {error && <Error error={error} />}
      {response && (<>
        <DomainDetails domain={response.domain} />
        <DomainStatusDetails domainStatus={response.status} />
      </>)}
    </div>
  );
}

