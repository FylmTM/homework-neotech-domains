import { Button, Card, ControlGroup, H3, InputGroup } from "@blueprintjs/core";
import React, { FC, FormEvent, useState } from "react";

interface Props {
  loading: boolean;
  onSearch: (domainName: string) => void;
}

export const DomainStatusForm: FC<Props> = ({ loading, onSearch }) => {
  const [domainName, setDomainName] = useState("");

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSearch(domainName);
  };

  return (
    <Card className="panel">
      <H3>Domain status</H3>
      <form onSubmit={handleSubmit}>
        <ControlGroup
          fill={true}
          vertical={false}>
          <InputGroup
            leftIcon="link"
            placeholder="Domain name"
            large
            value={domainName}
            onChange={(e: FormEvent<HTMLInputElement>) => setDomainName(e.currentTarget.value)}
          />
          <Button
            type="submit"
            icon="search"
            large
            loading={loading}
            disabled={loading}
          />
        </ControlGroup>
      </form>
    </Card>
  );
};
