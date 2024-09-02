import React from 'react'
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { oneDark } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { useSelector } from 'react-redux';
import yaml from 'js-yaml';

const customStyle = {
    ...oneDark,
    'pre[class*="language-"]': {
        ...oneDark['pre[class*="language-"]'],
        background: 'transparent',
    },
    'code[class*="language-"]': {
        ...oneDark['code[class*="language-"]'],
        background: 'transparent',
    },
};

const MemoizedSchemaYamlView = () => {
    const schemas = useSelector((state) => state.schema.value)

    const generateYaml = () => {
        const yamlObject = {
            components: {
                schemas: schemas.reduce((acc, schema) => {
                    acc[schema.name] = {
                        type: 'object',
                        properties: schema.properties,
                    };
                    return acc;
                }, {}),
            },
        };
        return yaml.dump(yamlObject);
    }
    return (
        <SyntaxHighlighter language="yaml" style={customStyle} className="w-full bg-transparent">
            {generateYaml()}
        </SyntaxHighlighter>
    )
}
const SchemaYamlView = React.memo(MemoizedSchemaYamlView)
export default SchemaYamlView
